package com.my.guitarstore.service;

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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void updateItem(Item item) {
        log.debug("Entering the method updateItem() ");
        // get existing item info from repo
        log.info("Executing itemRepository.read()");
        Optional<Item> existingItem = itemRepository.findById(item.getItemId());
        if(existingItem.isEmpty())
            throw new ItemNotFoundException("Exception occurs while orchestrating update. Item with the given ID Not Found.");
        copyNonNullProperties(item, existingItem);
        log.info("Executing itemRepository.save()::");
//        Item updatedItem = itemRepository.save(existingItem.get());
        Item updatedItem = itemRepository.save(item);

        log.debug("Exiting the method updateItem() ");
    }

    /**
     getNullPropertyNames()를 호출함으로써 String[]형태로
     내가 업데이트하고자하는 obj(src=item[line68])이 null 인 속성의 값을
     db에 있는 obj(target=existingItem[line68]) 에서 corresponding value 를 복사해온다.
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
      업데이트하려는 obj 가 null 값을 가지고 있는 속성의 이름들을 따와서 String[] 에 저장 후 반환한다.
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        // 그냥 return result; 하면 안되나?
        return emptyNames.toArray(result);
    }


}
