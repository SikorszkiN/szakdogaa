package com.szakdoga.szakdoga.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
       ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = NoEntityException.class)
    public ResponseEntity<Object> handleNoEntityException(NoEntityException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(apiException,badRequest);
    }

    @ExceptionHandler(value = DuplicateRecordException.class)
    public ResponseEntity<Object> handleDuplicateRecordException(DuplicateRecordException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(apiException,badRequest);
    }
}
