package com.szakdoga.szakdoga.app.exception;

public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String message){
        super(message);
    }
}
