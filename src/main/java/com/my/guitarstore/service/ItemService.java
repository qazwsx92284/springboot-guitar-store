package com.my.guitarstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.exception.ItemNotFoundException;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.model.error.ErrorSchema;
import com.my.guitarstore.repository.ItemRepository;
import com.my.guitarstore.response.ItemRO;
import com.my.guitarstore.util.IRequestValidator;
import com.my.guitarstore.util.IResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final IResponseMapper itemResponseMapper;
    private final IRequestValidator itemRequestValidator;
    private final ObjectMapper objectMapper;
  // private final ServletContext context;

    @Autowired
    private final ItemRepository itemRepository;

    public ResponseEntity addItem(Item item, String idToken, String apiHost) {
        // validate if required Item attributes are missing in request body
        if(!itemRequestValidator.validateAddRequest(item))
            return new ResponseEntity(new ErrorSchema("missing key attributes in request body", "/addIem", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        log.debug("Entering the method addItem()");
        log.info("Executing itemRepository.saveAndFlush()::");
        // insert item into repository(db)
       itemRepository.saveAndFlush(item);

        // return response obj (map Item -> ItemRO)
        ItemRO itemRO = itemResponseMapper.mapToItemResponse(item);
        log.debug("Exiting the method addItem()");
        return new ResponseEntity(itemRO, HttpStatus.CREATED);
    }

    public ResponseEntity getItemList(String idToken, String apiHost) {
        log.debug("Entering method getItemList()");
        log.info("Executing itemRepository.findAll()::");
        List<Item> itemList = itemRepository.findAll();
        return new ResponseEntity(itemList, HttpStatus.OK);
    }

    public void deleteItem(Long id) {
        log.info("Executing itemRepository.deleteById()::");
        itemRepository.deleteById(id);
    }

    public void updateItem(Item item) throws JsonProcessingException {
        log.debug("Entering the method updateItem() ");
        // get existing item info from repo
        log.info("Executing itemRepository.read()");
        Optional<Item> existingItem = itemRepository.findById(item.getItemId());
        if(existingItem.isEmpty())
            throw new ItemNotFoundException("Exception occurs while orchestrating update. Item with the given ID Not Found.");
        copyNonNullProperties(item, existingItem);
        log.info("Executing itemRepository.save()::");
       /* System.out.println("write item");
        System.out.println(objectMapper.writeValueAsString(item));
        System.out.println("write existingItem");
        System.out.println(objectMapper.writeValueAsString(existingItem));*/

        itemRepository.save(existingItem.get());
//        Item updatedItem = itemRepository.save(item);


        log.debug("Exiting the method updateItem() ");
    }

    /**
     getNullPropertyNames()??? ?????????????????? String[]?????????
     ?????? ??????????????????????????? obj(src=item[line68])??? null ??? ????????? ??????
     db??? ?????? obj(target=existingItem[line68]) ?????? corresponding value ??? ???????????????.
     */
    public void copyNonNullProperties(Object src, Object target) throws JsonProcessingException {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));

    }

    /**
      ????????????????????? obj ??? null ?????? ????????? ?????? ????????? ???????????? ????????? String[] ??? ?????? ??? ????????????.
     */
    public String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        System.out.println(Arrays.toString(result));
        return emptyNames.toArray(result);
    }
//    public static String[] getNullPropertyNames(Object source) {
//        final BeanWrapper src = new BeanWrapperImpl(source);
//        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//
//        Set<String> emptyNames = new HashSet<>();
//        for(java.beans.PropertyDescriptor pd : pds) {
//            Object srcValue = src.getPropertyValue(pd.getName());
//            if(srcValue == null)
//                emptyNames.add(pd.getName());
//        }
//        String[] result = new String[emptyNames.size()];
//        System.out.println(Arrays.toString(result));
//        // ?????? return result; ?????? ??????????
//        return emptyNames.toArray(result);
//    }


    public void updatePartialItem(Map<Object, Object> fields, Long id) {
        Optional<Item> item =  itemRepository.findById(id);
        if(item.isPresent()) {
            fields.forEach((key,value) -> {
                Field field = ReflectionUtils.findField(Item.class, (String) key);
                field.setAccessible(true);
                if(key == "itemState")
                    ReflectionUtils.setField(field, item.get(), Item.ItemState.valueOf(mapItemState(value)));
                else
                    ReflectionUtils.setField(field, item.get(), value);
            });
            itemRepository.save(item.get());
        }
    }

    protected String mapItemState(Object value) {
        switch (String.valueOf(value)) {
            case "New":
                return "NEW";
            case "Used":
                return "USED";
            default:
                throw new IllegalArgumentException("No such enum value");
        }
    }


}
