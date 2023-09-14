package com.u2d.projeto.exception;

public abstract class ValidationException extends RuntimeException{

    public ValidationException(String msg) {
        super(msg);
    }
}
