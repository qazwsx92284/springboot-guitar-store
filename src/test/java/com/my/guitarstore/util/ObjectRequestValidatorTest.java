package com.my.guitarstore.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
public class ObjectRequestValidatorTest {

    @InjectMocks
    private ObjectRequestValidator objectRequestValidator;

    @Test
    void validateAddRequestTest() {
        boolean result = objectRequestValidator.validateAddRequest(new Object());
        assertFalse(result);
    }

}
