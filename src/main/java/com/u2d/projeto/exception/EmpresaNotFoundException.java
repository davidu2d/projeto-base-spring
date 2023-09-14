package com.u2d.projeto.exception;

public class EmpresaNotFoundException extends ValidationException{

    private final static String MSG_EMPRESA_NOT_FOUND = "Empresa não encontrada";

    public EmpresaNotFoundException() {
        super(MSG_EMPRESA_NOT_FOUND);
    }
}
