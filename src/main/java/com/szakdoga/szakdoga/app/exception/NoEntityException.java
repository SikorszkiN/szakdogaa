package com.szakdoga.szakdoga.app.exception;

public class NoEntityException extends RuntimeException{

    public NoEntityException(String message) {
        super(message);
    }

    public NoEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
