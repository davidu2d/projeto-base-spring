package com.u2d.projeto.service.validator;

import com.u2d.projeto.exception.CNPJInvalidoException;
import org.springframework.stereotype.Component;

@Component
public class CNPJValidator {

    public void verificaCNPJNulo(String cnpj) {
        if (cnpj == null) {
            throw new CNPJInvalidoException();
        }
    }
}
