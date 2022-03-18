package com.my.guitarstore.util;

import org.springframework.stereotype.Component;

@Component
public class ObjectRequestValidator implements IRequestValidator {
    @Override
    public boolean validateAddRequest(Object object) {
        return false;
    }
}
