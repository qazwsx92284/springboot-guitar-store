package com.my.guitarstore.util;

import com.my.guitarstore.model.Item;
import com.my.guitarstore.response.ItemRO;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper implements IResponseMapper {
    @Override
    public ItemRO mapToItemResponse(Item item) {
        return null;
    }
}
