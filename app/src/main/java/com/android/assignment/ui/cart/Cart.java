package com.android.assignment.ui.cart;

import com.android.assignment.model.ProductModel;

import java.util.List;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public interface Cart {

    interface CartView {
        void onCartLoaded(List<ProductModel> cartList);

        void displayTotalPrice(int price);

    }

    interface CartPresenter {
        void onRemoveCartClick(ProductModel productModel);

        void onFetchCarData();

        void removeCartSuccessFully();

        void totalPriceCall();
    }

    interface CartModel {
        void removeCartFromDb(ProductModel productModel);

        List<ProductModel> getCartDataFromDb();

        int getTotalPrice();

    }
}
