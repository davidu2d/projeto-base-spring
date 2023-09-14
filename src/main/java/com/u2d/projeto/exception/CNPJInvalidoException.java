package com.u2d.projeto.exception;

public class CNPJInvalidoException extends ValidationException{

    private final static String MSG_CNPJ_INVALIDO = "CNPJ nulo ou inválido";

    public CNPJInvalidoException() {
        super(MSG_CNPJ_INVALIDO);
    }
}
