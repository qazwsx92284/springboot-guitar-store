package com.my.guitarstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity(name = "items")
@Data
@ApiModel(value = "Item", description = "The item object that contains the details pertaining to an item")
public class Item {

    public enum ItemState {
        NEW("New"), USED("Used");

        public final String value;

        ItemState(String value) {
            this.value =  value;
        }

        @JsonCreator
        public static ItemState convert(final String itemState) {
            ItemState inputItemState = Stream.of(ItemState.values()).filter(targetEnum -> targetEnum.value.equals(itemState))
                    .findFirst().orElse(null);
            if(ObjectUtils.isEmpty(inputItemState))
                throw new IllegalArgumentException("Invalid value for ItemState");
            return inputItemState;
        }

        @JsonValue
        public String getValue() {return value;}
    }

    @ApiModelProperty(name = "itemId", value = "Unique identifier for the item")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ApiModelProperty(name = "name", value = "Item name")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotNull
    private String name;

    @ApiModelProperty(name = "regularPrice", value = "regular price")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotNull
    private Double regularPrice;
//columnDefinition = "boolean default false",
    @ApiModelProperty(name = "couponApplicable", value = "Indicate whether coupon is applicable or not on the item")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column( name = "couponApplicable")
    private boolean couponApplicable;

    @ApiModelProperty(name = "sale discount", value = "Percentage of sale discount")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Transient
    private Discount discount;

    @ApiModelProperty(name = "itemState", value = "Indicate whether the item is used or new", example = "New")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotNull
    @Column(name = "itemState")
    @Enumerated(EnumType.STRING)
    private ItemState itemState;

    @ApiModelProperty(name = "amount", value = "The number of Item", example = "1")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotNull
    private Integer amount;
}
