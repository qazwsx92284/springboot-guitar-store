package com.my.guitarstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.guitarstore.ReadJsonFile;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StoreControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private StoreController storeController;

    Item item;

    {
        try {
            item = ReadJsonFile.getItemFromJson().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final String idToken = "1111-2jjd";
    private final String host = "moleBridge";

    @Test
    void createItemTest() {
        when(itemService.addItem(any(), any(), any())).thenReturn(new ResponseEntity(HttpStatus.CREATED));
        ResponseEntity mockResult = storeController.createItem(idToken, host, item);
    }

    @Test
    void getAllItemTest() {
        when(itemService.getItemList(any(),any())).thenReturn(new ResponseEntity(HttpStatus.OK));
        ResponseEntity mockResult = storeController.getAllItem(idToken, host);
    }

    @Test
    void deleteItemTest() {
        storeController.deleteItem(1L);
        verify(itemService, times(1)).deleteItem(any());
    }

    @Test
    void updateItemTest() throws JsonProcessingException {
        storeController.updateItem(idToken, host, item);
        verify(itemService, times(1)).updateItem(any());
    }

    @Test
    void updatePartialItemTest() {
        Map<Object, Object> fields = new HashMap<>();
        storeController.updatePartialItem(idToken, host, fields, 1L);
        verify(itemService, times(1)).updatePartialItem(any(),any());
    }

}
