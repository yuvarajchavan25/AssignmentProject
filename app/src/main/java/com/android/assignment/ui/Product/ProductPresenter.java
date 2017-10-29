package com.android.assignment.ui.Product;

import com.android.assignment.data.ProductData;
import com.android.assignment.model.ProductModel;
import com.android.assignment.model.ProductsResponse;
import com.android.volley.VolleyError;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class ProductPresenter implements Product.ProductPresenter {

    private Product.ProductView productView;
    private ProductData productData;

    public ProductPresenter(Product.ProductView productView) {
        this.productView = productView;
        productData = new ProductData(this);
    }

    @Override
    public void callProductData() {
        if(productView != null){
            productView.showProgress();
        }
        productData.productDataApiCall();
    }

    @Override
    public void onSuccess(ProductsResponse productsResponse) {
        productView.hideProgress();
        productView.onSuccess(productsResponse);
    }

    @Override
    public void onError(VolleyError error) {
        productView.hideProgress();
        productView.showError(error);
    }

    @Override
    public void onClickAddToCart(ProductModel productModel) {
        productData.addCartDataInDb(productModel);
    }

    @Override
    public void onRefreshOfProductData() {
        productData.refreshProductData();
    }
}
