package com.android.assignment.ui.Product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.assignment.R;
import com.android.assignment.model.ProductModel;
import com.android.assignment.utils.AppUtils;

import java.util.List;

/**
 * Created by Yuvaraj on 28-Oct-17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductModel> mProductsList;
    private Product.ProductPresenter mProductPresenter;

    public ProductsAdapter(Context context,
                           List<ProductModel> productModelList,
                           Product.ProductPresenter productPresenter) {
        this.mContext = context;
        this.mProductsList = productModelList;
        this.mProductPresenter = productPresenter;
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder holder, int position) {
        final ProductModel productModel = mProductsList.get(position);
        holder.productNameTextView.setText(productModel.getProductName());
        holder.productPriceTextView.setText(mContext.getResources().getString(R.string.price)
                + " " + productModel.getPrice());
        holder.vendorNameTextView.setText(productModel.getVendorName());
        holder.vendorAddressTextView.setText(productModel.getVendorAddress());
        AppUtils.setImage(mContext, productModel.getProductImg(), holder.productImageView);
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductPresenter.onClickAddToCart(productModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView productPriceTextView;
        private TextView vendorNameTextView;
        private TextView vendorAddressTextView;
        private Button addToCartButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productImageView = (ImageView) itemView.findViewById(R.id.product_image_view);
            productNameTextView = (TextView) itemView.findViewById(R.id.product_name);
            productPriceTextView = (TextView) itemView.findViewById(R.id.product_price);
            vendorNameTextView = (TextView) itemView.findViewById(R.id.vendor_name);
            vendorAddressTextView = (TextView) itemView.findViewById(R.id.vendor_address);
            addToCartButton = (Button) itemView.findViewById(R.id.add_cart_button);
        }
    }
}
