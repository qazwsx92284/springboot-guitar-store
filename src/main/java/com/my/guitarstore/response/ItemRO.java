package com.my.guitarstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@ApiModel(value = "ItemResponseObject", description = "Response object for Item")
@RequiredArgsConstructor
public class ItemRO {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long id;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    @ApiModelProperty(name = "regularPrice", value = "regular price")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double regularPrice;

    @JsonProperty("finalPrice")
    @ApiModelProperty("Final price : 디스카운트와 쿠폰이 모두 적용된 값")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double finalPrice;

    @JsonProperty("totalPrice")
    @ApiModelProperty("Total price : final price * amount")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double totalPrice;

    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer amount;

    @JsonProperty("itemState")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String itemState;


}
