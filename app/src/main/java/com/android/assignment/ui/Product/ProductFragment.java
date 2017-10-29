package com.android.assignment.ui.Product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.assignment.R;
import com.android.assignment.model.ProductsResponse;
import com.android.assignment.network.VolleyErrorHandling;
import com.android.volley.VolleyError;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class ProductFragment extends Fragment implements Product.ProductView {

    private static final int GRID_COLOUMN = 2;
    private RecyclerView mProductRecyclerView;
    private ProgressBar mProgressBar;

    private ProductPresenter productPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        productPresenter = new ProductPresenter(this);
        productPresenter.callProductData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (productPresenter != null)
            productPresenter.onRefreshOfProductData();
    }

    private void initView(View view) {
        mProductRecyclerView = (RecyclerView) view.findViewById(R.id.product_recycler_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(VolleyError error) {
        VolleyErrorHandling.checkErrorType(error, getActivity());
    }

    @Override
    public void onSuccess(ProductsResponse productsResponse) {
        mProductRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_COLOUMN));
        ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(),
                productsResponse.getProductList(), productPresenter);
        mProductRecyclerView.setAdapter(productsAdapter);
    }
}
