package com.android.assignment.data;

import com.android.assignment.AssignmentApp;
import com.android.assignment.data.database.DatabaseHandler;
import com.android.assignment.ui.Product.Product;
import com.android.assignment.model.ProductModel;
import com.android.assignment.model.ProductsResponse;
import com.android.assignment.network.VolleySingleton;
import com.android.assignment.utils.ApiCalls;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class ProductData implements Product.ProductModel, Response.Listener<JSONObject>,
        Response.ErrorListener {

    private Product.ProductPresenter productPresenter;
    private DatabaseHandler databaseHandler;
    private ProductsResponse productsResponse;

    public ProductData(Product.ProductPresenter productPresenter) {
        this.productPresenter = productPresenter;
        databaseHandler = new DatabaseHandler(AssignmentApp.getAppContext());
    }

    @Override
    public void productDataApiCall() {
        productDataApiRequest();
    }

    @Override
    public void addCartDataInDb(ProductModel productModel) {
        databaseHandler.addProductInDb(productModel);
        productListExcludingCart();
    }

    @Override
    public void refreshProductData() {
        productListExcludingCart();
    }

    private void productDataApiRequest() {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ApiCalls.API_URL, null, this, this);
        VolleySingleton.getInstance(AssignmentApp.getAppContext()).addToRequestQueue(jsonRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new GsonBuilder().create();
        productsResponse = gson.fromJson(response.toString(), ProductsResponse.class);
        productListExcludingCart();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        productPresenter.onError(error);
    }

    private void productListExcludingCart() {
        if (productsResponse != null) {
            List<ProductModel> productList = new ArrayList<>();
            for (ProductModel productModel : productsResponse.getProductList()) {
                if (!databaseHandler.isProductExistInCart(productModel.getProductName())) {
                    productList.add(productModel);
                }
            }
            ProductsResponse productsResponse = new ProductsResponse();
            productsResponse.setProductList(productList);
            productPresenter.onSuccess(productsResponse);
        }
    }
}
