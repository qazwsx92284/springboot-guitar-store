package com.my.guitarstore.constants;

public class SwaggerConstant {

    public static final String ADD_ITEM_DESC = "Post item to inventory";
    public static final String ADD_ITEM_NOTES = "The following fields must be proveide in the request payload while adding an item \n\n"
            +"\n\t - name"+ "\n\t - regularPrice" +"\n\n\n"
            +"The following attributes may be required to add the item to inventory and hence must be provided by the caller when hitting this endpoint. \n\n"
            +"\n\t - itemState";



}
