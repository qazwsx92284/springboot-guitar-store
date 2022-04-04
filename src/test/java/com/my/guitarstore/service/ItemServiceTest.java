package com.my.guitarstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.ReadJsonFile;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.repository.ItemRepository;
import com.my.guitarstore.response.ItemRO;
import com.my.guitarstore.util.ItemRequestValidator;
import com.my.guitarstore.util.ItemResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ItemRequestValidator itemRequestValidator;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemResponseMapper itemResponseMapper;



    @Test
    void addItemTest_valid_reqBody() throws IOException {
        Item validItem = ReadJsonFile.getItemFromJson().get(0);

        when(itemRequestValidator.validateAddRequest(any())).thenReturn(true);
        when(itemRepository.saveAndFlush(any())).thenReturn(validItem);
        when(itemResponseMapper.mapToItemResponse(any())).thenReturn(new ItemRO());
        ResponseEntity result = itemService.addItem(validItem, "eydifsjeifj", "traveling");
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getItemListTest() throws IOException {
        List<Item> itemList = ReadJsonFile.getItemFromJson();
        when(itemRepository.findAll()).thenReturn(itemList);
        ResponseEntity result = itemService.getItemList("idT", "host");
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }



}