package com.my.guitarstore.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ExceptionHandlerTest {

    @InjectMocks
    private ExceptionHandler exceptionHandler;

    private HttpMessageNotReadableException me = new HttpMessageNotReadableException("me");


    @Test
    void handleHttpMessageNotReadableExceptionTest_when_valueInstantiationException() {
        ValueInstantiationException vem = mock(ValueInstantiationException.class);
        me.initCause(vem);
        when(vem.getCause()).thenReturn(vem);
        when(vem.getLocalizedMessage()).thenReturn("msg");
       ResponseEntity mockResult = exceptionHandler.handleHttpMessageNotReadableException(me);
       assertEquals(HttpStatus.BAD_REQUEST, mockResult.getStatusCode());
    }

    @Test
    void handleHttpMessageNotReadableException_when_not_valueInstantiationException() {
        ResponseEntity mockResult = exceptionHandler.handleHttpMessageNotReadableException(me);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, mockResult.getStatusCode());
    }


}
