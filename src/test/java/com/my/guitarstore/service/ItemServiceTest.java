package com.my.guitarstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.ReadJsonFile;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.repository.ItemRepository;
import com.my.guitarstore.util.ItemRequestValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

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

    Item validItem = ReadJsonFile.getItemFromJson().get(0);

    ItemServiceTest() throws IOException {}


    @Test
    void addItemTest_valid_reqBody() {
        when(itemRequestValidator.validateAddRequest(any())).thenReturn(true);
        when(itemRepository.saveAndFlush(any())).thenReturn(validItem);
        ResponseEntity result = itemService.addItem(validItem, "eydifsjeifj", "traveling");
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }



}