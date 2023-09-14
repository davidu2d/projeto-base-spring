package com.u2d.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.u2d.projeto.dto.EmpresaDTO;
import com.u2d.projeto.exception.EmpresaNotFoundException;
import com.u2d.projeto.service.validator.EmpresaValidator;
import com.u2d.projeto.util.DateUtil;
import com.u2d.projeto.util.RequestUtil;
import com.u2d.projeto.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.u2d.projeto.model.Empresa;
import com.u2d.projeto.repository.EmpresaRepository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmpresaService {

	private static final String CNPJ = "cnpj";
	private static final String URL_RECEITA = "https://www.receitaws.com.br/v1/cnpj";

	@Autowired
	private EmpresaRepository repository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private EmpresaValidator validator;

	public List<Empresa> findAll() {
		return repository.findAll();
	}

	public Optional<EmpresaDTO> save(EmpresaDTO empresaDTO) {
		Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
		if(empresa.getCnpj() != null){
			empresa.setCnpj(StringUtil.removeMascara(empresa.getCnpj()));
		}
		empresa.setDataAbertura(empresaDTO.getDataAbertura());
		return Optional.of(modelMapper.map(repository.save(empresa), EmpresaDTO.class));
	}

	public Empresa findById(Long codigo) {
		Optional<Empresa> empresa = repository.findById(codigo);
		if(empresa.isPresent())
			return empresa.get();
		return null;
	}

	public Empresa update(Long codigo, Empresa empresa) {
		Empresa empresaSalva = findById(codigo);
		if(empresaSalva.getId() != null)
			return repository.save(empresa);
		return null;
	}

	public void delete(Long codigo) {
		Empresa empresaSalva = findById(codigo);
		if(empresaSalva != null)
			repository.delete(empresaSalva);
	}

	public Optional<EmpresaDTO> findByCnpjReceita(Map<String, String[]> parameterMap) {
		String cnpj = RequestUtil.extrairParametro(parameterMap, CNPJ);
		validator.verificaCNPJEmpresa(cnpj);
		return Optional.of(getEmpresaDTOResponseEntity(cnpj)).orElseThrow(EmpresaNotFoundException::new);
	}

	private Optional<EmpresaDTO> getEmpresaDTOResponseEntity(String cnpj) {
		try {
			log.info("Iniciando a pesquisa de empresa na receita com CNPJ: {}", cnpj);
			ResponseEntity<EmpresaDTO> retorno = restTemplate.getForEntity(RequestUtil.montaPathRequisicao(URL_RECEITA, StringUtil.removeMascara(cnpj)), EmpresaDTO.class);
			if(retorno != null & retorno.getBody() != null & retorno.getBody().getAbertura() != null){
				retorno.getBody().setDataAbertura(DateUtil.stringFormatBrasilToLocalDate(retorno.getBody().getAbertura()));
				retorno.getBody().setAbertura(null);
				Thread.sleep(20000);
				log.info("Finalizando a pesquisa de empresa");
				return Optional.of(retorno.getBody());
			}
		}catch (HttpStatusCodeException | InterruptedException ex){
			log.error("Erro ao buscar empresa na receita", ex.getMessage(), ex);
		}
		return Optional.of(null);
	}
}
