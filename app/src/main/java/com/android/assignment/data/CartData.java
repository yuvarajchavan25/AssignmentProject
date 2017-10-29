package com.android.assignment.data;

import com.android.assignment.AssignmentApp;
import com.android.assignment.data.database.DatabaseHandler;
import com.android.assignment.ui.cart.Cart;
import com.android.assignment.model.ProductModel;

import java.util.List;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class CartData implements Cart.CartModel {

    private Cart.CartPresenter cartPresenter;
    private DatabaseHandler databaseHandler;

    public CartData(Cart.CartPresenter cartPresenter) {
        this.cartPresenter = cartPresenter;
        databaseHandler = new DatabaseHandler(AssignmentApp.getAppContext());
    }

    @Override
    public void removeCartFromDb(ProductModel productModel) {
        databaseHandler.removeFromCart(productModel);
        cartPresenter.removeCartSuccessFully();
    }

    @Override
    public List<ProductModel> getCartDataFromDb() {
        return databaseHandler.getAllProductFromDb();
    }

    @Override
    public int getTotalPrice() {
        int totalPrice = 0;
        for (ProductModel productModel : databaseHandler.getAllProductFromDb()) {
            totalPrice = totalPrice + Integer.parseInt(productModel.getPrice());
        }
        return totalPrice;
    }


}
