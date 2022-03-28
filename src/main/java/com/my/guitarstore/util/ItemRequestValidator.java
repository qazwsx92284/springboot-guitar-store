package com.my.guitarstore.util;

import com.my.guitarstore.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Qualifier("itemRequestValidator")
public class ItemRequestValidator extends ObjectRequestValidator {

    @Override
    public boolean validateAddRequest(Object object) {
        Item item = (Item)object;
        if(Objects.isNull(item.getItemState()) || Objects.isNull(item.getRegularPrice()))
            return false;
        return true;
    }
}
