package com.my.guitarstore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.model.Discount;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.response.ItemRO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Component("itemResponseMapper")
@Slf4j
public class ItemResponseMapper extends  ResponseMapper {

    ObjectMapper objectMapper;

    @Override
    public ItemRO mapToItemResponse(Item item) {
        ItemRO itemRO = new ItemRO();
        itemRO.setAmount(item.getAmount());
        itemRO.setId(item.getItemId());
        itemRO.setName(item.getName());
        itemRO.setRegularPrice(item.getRegularPrice());
        itemRO.setFinalPrice(calculateFinalPrice(item.getDiscount(), item.getRegularPrice()));
        itemRO.setTotalPrice(itemRO.getAmount()* itemRO.getFinalPrice());
        itemRO.setItemState(item.getItemState().value);
        return itemRO;
    }

    protected Double calculateFinalPrice(Discount discount, Double regularPrice) {
        double finalPrice = regularPrice;
        if(ObjectUtils.isEmpty(discount))
            return finalPrice;

        Map<String, Integer> discountMap = objectMapper.convertValue(discount, Map.class);
        for (String discountType : discountMap.keySet()) {
            switch (discountType) {
                case "saleDiscount":
                    finalPrice = regularPrice*(100-discountMap.get(discountType))/100;
                case "couponDiscount":
                    finalPrice = finalPrice*(100- discountMap.get(discountType))/100;
            }
        }
        return finalPrice;
    }
}
