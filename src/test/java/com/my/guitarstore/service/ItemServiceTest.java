package com.my.guitarstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.ReadJsonFile;
import com.my.guitarstore.exception.ItemNotFoundException;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.repository.ItemRepository;
import com.my.guitarstore.response.ItemRO;
import com.my.guitarstore.util.ItemRequestValidator;
import com.my.guitarstore.util.ItemResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @InjectMocks
    @Spy
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
    void addItemTest_invalid_reqBody() throws IOException {
        Item invalidItem = ReadJsonFile.getItemFromJson().get(1);
        when(itemRequestValidator.validateAddRequest(any())).thenReturn(false);
        ResponseEntity result = itemService.addItem(invalidItem, "idT", "apiHost");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getItemListTest() throws IOException {
        List<Item> itemList = ReadJsonFile.getItemFromJson();
        when(itemRepository.findAll()).thenReturn(itemList);
        ResponseEntity result = itemService.getItemList("idT", "host");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteItemTest() {
        doNothing().when(itemRepository).deleteById(any());
        itemService.deleteItem(1234L);
        verify(itemRepository, atLeastOnce()).deleteById(any());
    }

    @Test
    void updateItemTest_existingItem_exist() throws JsonProcessingException {
        when(itemRepository.findById(any())).thenReturn(getOptionalItem());
       // doNothing().when(itemService).copyNonNullProperties(any(), any());
        when(itemRepository.save(any())).thenReturn(new Item());
        itemService.updateItem(getItem());
        verify(itemRepository, times(1)).save(any());
        verify(itemService, times(1)).copyNonNullProperties(any(), any());
    }

    @Test
    void updateItemTest_existingItem_NotExist() {
        Optional<Item> emptyOptionalItem = Optional.empty();
        when(itemRepository.findById(any())).thenReturn(emptyOptionalItem);
        assertThrows(ItemNotFoundException.class, () -> {
            itemService.updateItem(getItem());
        });
    }

    @Test
    void copyNonNullPropertiesTest() throws JsonProcessingException {
        Item item = new Item();
        item.setName("String");
        itemService.copyNonNullProperties(item, getItem());
        verify(itemService, times(1)).getNullPropertyNames(any());
    }


    @Test
    void updatePartialItemTest() {
        when(itemRepository.findById(any())).thenReturn(getOptionalItem());
        Map<Object, Object> fields = new HashMap<>();
        fields.put("itemId",1L);
        fields.put("name","Fender");
        fields.put("regularPrice", 800.99);
        fields.put("itemState", "New");
        when(itemRepository.save(any())).thenReturn(new Item());
        itemService.updatePartialItem(fields, 1L);
        verify(itemRepository, atLeastOnce()).save(any());
//        List<Object> values = new ArrayList<>(Arrays.asList(1, 800.99, "Fender", "New"));
//        System.out.println(values);
//        verify(itemService, atLeastOnce()).mapItemState(eq(values));

    }

    @Test
    void updatePartialItemTest_when_fieldsMap_is_empty() {
        Item item = new Item();
        item.setItemId(1L);
        Optional<Item> optionalItem = Optional.of(item);
        when(itemRepository.findById(any())).thenReturn(optionalItem);
        Map<Object, Object> fields = new HashMap<>();
        when(itemRepository.save(any())).thenReturn(new Item());
        itemService.updatePartialItem(fields, 1L);
        verify(itemRepository, atLeastOnce()).save(any());
    }

    private Optional<Item> getOptionalItem() {
        Optional<Item> optionalItem = Optional.of(getItem());
        return optionalItem;
    }

    private Item getItem() {
        Item item = new Item();
        item.setItemState(Item.ItemState.NEW);
        item.setRegularPrice(19.99);
        item.setAmount(2);
        item.setItemId(1234L);
        item.setName("String");
        return item;
    }





}