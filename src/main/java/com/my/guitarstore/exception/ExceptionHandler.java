package com.my.guitarstore.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.my.guitarstore.model.error.ErrorSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandler {

   // private final ServletContext context;

    public ResponseEntity<ErrorSchema> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if(e.getCause() instanceof ValueInstantiationException) {
            ValueInstantiationException instantiationException = (ValueInstantiationException) e.getCause();
            return new ResponseEntity(new ErrorSchema(instantiationException.getCause().getLocalizedMessage(), "path", LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new ErrorSchema(e.getLocalizedMessage(), "path", LocalDateTime.now()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

