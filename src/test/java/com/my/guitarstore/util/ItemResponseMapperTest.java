package com.my.guitarstore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.ReadJsonFile;
import com.my.guitarstore.model.Discount;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.response.ItemRO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ItemResponseMapperTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemResponseMapper itemResponseMapper;

    Item item = ReadJsonFile.getItemFromJson().get(0);

    ItemResponseMapperTest() throws IOException {
    }

    @Test
    void mapToItemResponseTest() {
        ItemRO mockResult = itemResponseMapper.mapToItemResponse(item);
        assertEquals(item.getItemId(), mockResult.getId());
        assertEquals(item.getAmount(), mockResult.getAmount());
        assertEquals(item.getName(), mockResult.getName());
        assertEquals(item.getRegularPrice(), mockResult.getRegularPrice());
    }

    @Test
    void calculateFinalPriceTest() {
        Map<String, Integer> discountMap = new HashMap();
        when(objectMapper.convertValue(any(), eq(Map.class))).thenReturn(discountMap);
        Discount discount = new Discount();
        discount.setCouponDiscount(20);
        Double mockResult = itemResponseMapper.calculateFinalPrice(discount, 20d);
        assertEquals(16d, mockResult);
    }


}