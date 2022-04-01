package com.my.guitarstore.util;

import com.my.guitarstore.ReadJsonFile;
import com.my.guitarstore.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class ItemRequestValidatorTest {

    @InjectMocks
    private ItemRequestValidator itemRequestValidator;


    @Test
    void validateAddRequestTest_Valid() {
        Item item = getItem();
//        Object object = null;
//        Item itemCast = (Item)object;
//        itemCast = (Item) ReadJsonFile.getListFromJson("src/test/java/resources/ItemList.json").get(0);
        boolean result = itemRequestValidator.validateAddRequest(item);
        assertTrue(result);
    }

    @Test
    void validateAddRequestTest_Invalid() throws IOException {
        Item item = ReadJsonFile.getItemFromJson().get(1);
        assertFalse(itemRequestValidator.validateAddRequest(item));
    }

    private Item getItem() {
        Item item = new Item();
        item.setRegularPrice(19.99);
        item.setItemState(Item.ItemState.NEW);
        return item;
    }

}