package com.android.assignment.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.assignment.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuvaraj on 28-Oct-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productData";
    private static final String TABLE_PRODUCT = "product";

    private static final String KEY_ID = "id";
    private static final String PRODUCT_NAME = "productName";
    private static final String PRODUCT_PRICE = "price";
    private static final String VENDOR_NAME = "vendorName";
    private static final String VENDOR_ADDRESS = "vendorAddress";
    private static final String PRODUCT_IMAGE = "productImg";
    private static final String PHONE_NUMBER = "phoneNumber";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + PRODUCT_NAME + " TEXT," + PRODUCT_PRICE + " TEXT,"
                + VENDOR_NAME + " TEXT," + VENDOR_ADDRESS + " TEXT," + PRODUCT_IMAGE + " TEXT,"
                + PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    /**
     * Add Product Data ti databse to show in cart
     *
     * @param productModel product data
     */
    public void addProductInDb(ProductModel productModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME, productModel.getProductName());
        contentValues.put(PRODUCT_PRICE, productModel.getPrice());
        contentValues.put(VENDOR_NAME, productModel.getVendorName());
        contentValues.put(VENDOR_ADDRESS, productModel.getVendorAddress());
        contentValues.put(PRODUCT_IMAGE, productModel.getProductImg());
        contentValues.put(PHONE_NUMBER, productModel.getPhoneNumber());

        db.insert(TABLE_PRODUCT, null, contentValues);
        db.close();
    }

    /**
     * get all the product cart data from database
     *
     * @return list of data added in databse
     */
    public List<ProductModel> getAllProductFromDb() {
        List<ProductModel> productInCartList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(Integer.parseInt(cursor.getString(0)));
                productModel.setProductName(cursor.getString(1));
                productModel.setPrice(cursor.getString(2));
                productModel.setVendorName(cursor.getString(3));
                productModel.setVendorAddress(cursor.getString(4));
                productModel.setProductImg(cursor.getString(5));
                productModel.setPhoneNumber(cursor.getString(6));
                productInCartList.add(productModel);
            }
            while (cursor.moveToNext());
        }
        return productInCartList;
    }

    /**
     * Delete the cart data from database
     *
     * @param productModel product data
     */
    public void removeFromCart(ProductModel productModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, KEY_ID + " = ?",
                new String[]{String.valueOf(productModel.getId())});
        db.close();
    }

    public boolean isProductExistInCart(String productName) {

        String whereClause = PRODUCT_NAME + " = ?";
        String[] whereArgs = new String[]{productName};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT, null, whereClause, whereArgs, null, null, null);
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exist;
    }

}
