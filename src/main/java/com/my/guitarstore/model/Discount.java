package com.my.guitarstore.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Discount object which holds all kinds of discount")
public class Discount {

    private Integer couponDiscount;

    private Integer saleDiscount;

//    private int membershipDiscount;

//    private int empDiscount;
}
