package com.my.guitarstore.service;

import com.my.guitarstore.model.Item;
import com.my.guitarstore.model.error.ErrorSchema;
import com.my.guitarstore.repository.ItemRepository;
import com.my.guitarstore.response.ItemRO;
import com.my.guitarstore.util.IRequestValidator;
import com.my.guitarstore.util.IResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final IResponseMapper itemResponseMapper;
    private final IRequestValidator itemRequestValidator;
  // private final ServletContext context;

    @Autowired
    private ItemRepository itemRepository;

    public ResponseEntity addItem(Item item, String idToken, String apiHost) {
        // validate if required Item attributes are missing in request body
        if(!itemRequestValidator.validateAddRequest(item))
            return new ResponseEntity(new ErrorSchema("missing key attributes in request body", "/addIem", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        log.debug("Entering the method addItem()");
        // insert item into repository(db)
        itemRepository.saveAndFlush(item);

        // return response obj (map Item -> ItemRO)
        ItemRO itemRO = itemResponseMapper.mapToItemResponse(item);
        log.debug("Exiting the method addItem()");
        return new ResponseEntity(itemRO, HttpStatus.CREATED);
    }

    public ResponseEntity getItemList(String idToken, String apiHost) {
        log.debug("Entering method getItemList()");
        List<Item> itemList = itemRepository.findAll();
        return new ResponseEntity(itemList, HttpStatus.OK);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
