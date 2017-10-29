package com.android.assignment.ui.cart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.assignment.R;
import com.android.assignment.model.ProductModel;

import java.util.List;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class CartFragment extends Fragment implements Cart.CartView {

    private static final int CALL_PHONE_PERMISSION = 1;
    private RecyclerView mCartRecyclerView;
    private CartPresenter cartPresenter;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private String phoneNumber;
    private TextView cartEmptyTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        cartPresenter = new CartPresenter(this);
    }

    private void initView(View view) {
        mCartRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recycler_view);
        totalPriceTextView = (TextView) view.findViewById(R.id.total_price);
        cartEmptyTextView = (TextView) view.findViewById(R.id.cart_empty);
    }

    @Override
    public void onCartLoaded(List<ProductModel> cartList) {
        setAdapterData(cartList);
        cartPresenter.totalPriceCall();
        if (cartList.size() > 0) {
            cartEmptyTextView.setVisibility(View.GONE);
        } else {
            cartEmptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayTotalPrice(int price) {
        totalPriceTextView.setText(String.format(getString(R.string.total_price), price));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (cartPresenter != null)
            cartPresenter.onFetchCarData();
    }

    private void setAdapterData(List<ProductModel> cartList) {
        mCartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartAdapter = new CartAdapter(getActivity(), cartList, cartPresenter, this);
        mCartRecyclerView.setAdapter(cartAdapter);
    }

    public void call(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        if (isPermissionGranted()) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }

    private boolean isPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_PHONE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(phoneNumber);
                }
            }
        }
    }
}


