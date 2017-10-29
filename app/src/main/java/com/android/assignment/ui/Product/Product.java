package com.android.assignment.ui.Product;

import com.android.assignment.model.ProductsResponse;
import com.android.volley.VolleyError;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public interface Product {


    interface ProductView {

        void showProgress();

        void hideProgress();

        void showError(VolleyError error);

        void onSuccess(ProductsResponse productsResponse);
    }

    interface ProductPresenter {
        void callProductData();

        void onSuccess(ProductsResponse productsResponse);

        void onError(VolleyError error);

        void onClickAddToCart(com.android.assignment.model.ProductModel productModel);

        void onRefreshOfProductData();
    }

    interface ProductModel {
        void productDataApiCall();

        void addCartDataInDb(com.android.assignment.model.ProductModel productModel);

        void refreshProductData();
    }


}
