package com.my.guitarstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.guitarstore.constants.Constant;
import com.my.guitarstore.constants.SwaggerConstant;
import com.my.guitarstore.model.Item;
import com.my.guitarstore.model.error.ErrorSchema;
import com.my.guitarstore.response.ItemRO;
import com.my.guitarstore.service.ItemService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Api(value = "/item", tags = {"Item", "User"})
@Tag(name = "Item", description = "Item related endpoints")
@Tag(name = "User", description = "User related endpoints")
@Slf4j
@Validated
@RequiredArgsConstructor
public class StoreController {

    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value="/item")
    @ApiOperation(value = SwaggerConstant.ADD_ITEM_DESC, notes = SwaggerConstant.ADD_ITEM_NOTES)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Item added", response = ItemRO.class),
                    @ApiResponse(code = 400, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class),
                    @ApiResponse(code = 401, message = Constant.ErrorMessages.UNAUTHORIZED, response = ErrorSchema.class),
                    @ApiResponse(code = 403, message = Constant.ErrorMessages.FORBIDDEN, response = ErrorSchema.class),
                    @ApiResponse(code = 404, message = Constant.ErrorMessages.NOT_FOUND, response = ErrorSchema.class),
                    @ApiResponse(code = 500, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class)
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ItemRO> createItem(
            @ApiParam(value = "id token. Required to invoke api",
                       example = "id:1k3jfdi2fnv",
                        required = false)  @RequestHeader(value="id-token") String idToken,
            @ApiParam(value = "api host", example = "abc-cc-plane", required = false,
                    defaultValue = "abd-cc-plane") @RequestHeader(value="api-key", required = false) String apiHost,
            @ApiParam(value = "request item object", required = true)
            @RequestBody @Valid final Item item)
    {
        log.debug("Entering the method createIem()");
        return itemService.addItem(item, idToken, apiHost);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value="/item")
    @ApiOperation(value = SwaggerConstant.ADD_ITEM_DESC, notes = SwaggerConstant.ADD_ITEM_NOTES)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Item List Retrieved", response = ItemRO.class, responseContainer = "List"),
                    @ApiResponse(code = 401, message = Constant.ErrorMessages.UNAUTHORIZED, response = ErrorSchema.class),
                    @ApiResponse(code = 403, message = Constant.ErrorMessages.FORBIDDEN, response = ErrorSchema.class),
                    @ApiResponse(code = 404, message = Constant.ErrorMessages.NOT_FOUND, response = ErrorSchema.class),
                    @ApiResponse(code = 500, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllItem(
            @ApiParam(value = "id token. Required to invoke api",
                    example = "id:1k3jfdi2fnv",
                    required = true)  @RequestHeader(value="id-token") String idToken,
            @ApiParam(value = "api host", example = "abc-cc-plane", required = false,
                    defaultValue = "abd-cc-plane") @RequestHeader(value="api-key", required = false) String apiHost)
    {
        log.debug("Entering the method getAllItem()");
        return itemService.getItemList( idToken, apiHost);
    }

    @DeleteMapping(value = "/item/{id}")
    @ApiOperation(value = "Delete Item from DB", notes = "Id is required to orchetrate deletion")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Item Removed"),
                    @ApiResponse(code = 401, message = Constant.ErrorMessages.UNAUTHORIZED, response = ErrorSchema.class),
                    @ApiResponse(code = 403, message = Constant.ErrorMessages.FORBIDDEN, response = ErrorSchema.class),
                    @ApiResponse(code = 404, message = Constant.ErrorMessages.NOT_FOUND, response = ErrorSchema.class),
                    @ApiResponse(code = 500, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(
            @ApiParam(value = "ID for item to be deleted", example="1234L") @PathVariable Long id
    ) {
        log.debug("Entering the method deleteItem() :: StoreController class");
        itemService.deleteItem(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value="/item")
    @ApiOperation(value = SwaggerConstant.UPDATE_ITEM_DESC, notes = SwaggerConstant.UPDATE_ITEM_NOTES)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Item added", response = ItemRO.class),
                    @ApiResponse(code = 400, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class),
                    @ApiResponse(code = 401, message = Constant.ErrorMessages.UNAUTHORIZED, response = ErrorSchema.class),
                    @ApiResponse(code = 403, message = Constant.ErrorMessages.FORBIDDEN, response = ErrorSchema.class),
                    @ApiResponse(code = 404, message = Constant.ErrorMessages.NOT_FOUND, response = ErrorSchema.class),
                    @ApiResponse(code = 500, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class)
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(
            @ApiParam(value = "id token. Required to invoke api",
                    example = "id:1k3jfdi2fnv",
                    required = false)  @RequestHeader(value="id-token") String idToken,
            @ApiParam(value = "api host", example = "abc-cc-plane", required = false,
                    defaultValue = "abd-cc-plane") @RequestHeader(value="api-key", required = false) String apiHost,
            @ApiParam(value = "request item object", required = true)
            @RequestBody @Valid final Item item) throws JsonProcessingException
//            @ApiParam(value="itemId to be updated", example="1234L", required = false) @PathVariable Long itemId)
    {
        log.debug("Entering the method updateItem()");
        itemService.updateItem(item);
    }

    // TODO: 3/24/2022 need to implement update method to update only changed fields & think about PathVariable
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value="/item/{id}")
    @ApiOperation(value = SwaggerConstant.UPDATE_ITEM_DESC, notes = SwaggerConstant.UPDATE_ITEM_NOTES)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Item added", response = ItemRO.class),
                    @ApiResponse(code = 400, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class),
                    @ApiResponse(code = 401, message = Constant.ErrorMessages.UNAUTHORIZED, response = ErrorSchema.class),
                    @ApiResponse(code = 403, message = Constant.ErrorMessages.FORBIDDEN, response = ErrorSchema.class),
                    @ApiResponse(code = 404, message = Constant.ErrorMessages.NOT_FOUND, response = ErrorSchema.class),
                    @ApiResponse(code = 500, message = Constant.ErrorMessages.INVALID_REQUEST, response = ErrorSchema.class)
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePartialItem(
            @ApiParam(value = "id token. Required to invoke api",
                    example = "id:1k3jfdi2fnv",
                    required = false)  @RequestHeader(value="id-token") String idToken,
            @ApiParam(value = "api host", example = "abc-cc-plane", required = false,
                    defaultValue = "abd-cc-plane") @RequestHeader(value="api-key", required = false) String apiHost,
            @ApiParam(value = "request item object", required = true)
            @RequestBody @Valid final Map<Object, Object> fields,
            @ApiParam(value="itemId to be updated", example="1234L", required = false) @PathVariable Long itemId)
    {
        log.debug("Entering the method updateItem()");
        itemService.updatePartialItem(fields, itemId);
    }
}
