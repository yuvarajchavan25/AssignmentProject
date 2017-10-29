package com.android.assignment.ui.cart;

import com.android.assignment.data.CartData;
import com.android.assignment.model.ProductModel;

import java.util.List;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class CartPresenter implements Cart.CartPresenter {

    private Cart.CartView cartView;
    private CartData cartData;

    public CartPresenter(Cart.CartView cartViewListener) {
        this.cartView = cartViewListener;
        cartData = new CartData(this);
    }

    @Override
    public void onRemoveCartClick(ProductModel productModel) {
        cartData.removeCartFromDb(productModel);
    }


    @Override
    public void onFetchCarData() {
        List<ProductModel> cartList = cartData.getCartDataFromDb();
        cartView.onCartLoaded(cartList);
    }

    @Override
    public void removeCartSuccessFully() {
        List<ProductModel> cartList = cartData.getCartDataFromDb();
        cartView.onCartLoaded(cartList);
    }

    @Override
    public void totalPriceCall() {
        cartView.displayTotalPrice(cartData.getTotalPrice());
    }
}
