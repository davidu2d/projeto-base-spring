package com.u2d.projeto.service.validator;

import com.u2d.projeto.exception.CNPJInvalidoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmpresaValidator {

    @Autowired
    private CNPJValidator cnpjValidator;

    public void verificaCNPJEmpresa(String cnpj) {
        cnpjValidator.verificaCNPJNulo(cnpj);
    }
}
