package com.my.guitarstore.util;

import com.my.guitarstore.model.Item;
import com.my.guitarstore.response.ItemRO;

public interface IResponseMapper {

    public ItemRO mapToItemResponse(Item item);
}
