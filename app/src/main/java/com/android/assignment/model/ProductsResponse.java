package com.android.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yuvaraj on 28-Oct-17.
 */

public class ProductsResponse {

    @SerializedName("products")
    private List<ProductModel> productList;

    public List<ProductModel> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductModel> productList) {
        this.productList = productList;
    }
}
