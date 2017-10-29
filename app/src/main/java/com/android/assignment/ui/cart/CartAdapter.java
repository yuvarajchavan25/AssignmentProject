package com.android.assignment.ui.cart;

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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductModel> mCartList;
    private Cart.CartPresenter cartPresenter;
    private CartFragment cartFragment;

    public CartAdapter(Context context,
                       List<ProductModel> cartList,
                       Cart.CartPresenter cartPresenter,
                       CartFragment cartFragment) {
        this.mContext = context;
        this.mCartList = cartList;
        this.cartPresenter = cartPresenter;
        this.cartFragment = cartFragment;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new CartAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        final ProductModel productModel = mCartList.get(position);
        holder.productNameTextView.setText(productModel.getProductName());
        holder.productPriceTextView.setText(mContext.getResources().getString(R.string.price)
                + "\n "+productModel.getPrice());
        holder.vendorNameTextView.setText(productModel.getVendorName());
        holder.vendorAddressTextView.setText(productModel.getVendorAddress());
        AppUtils.setImage(mContext, productModel.getProductImg(), holder.productImageView);
        holder.callVendorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartFragment.call(productModel.getPhoneNumber());
            }
        });
        holder.removeFromCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartPresenter.onRemoveCartClick(productModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView productPriceTextView;
        private TextView vendorNameTextView;
        private TextView vendorAddressTextView;
        private Button callVendorButton;
        private Button removeFromCartButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productImageView = (ImageView) itemView.findViewById(R.id.product_image_view);
            productNameTextView = (TextView) itemView.findViewById(R.id.product_name);
            productPriceTextView = (TextView) itemView.findViewById(R.id.product_price);
            vendorNameTextView = (TextView) itemView.findViewById(R.id.vendor_name);
            vendorAddressTextView = (TextView) itemView.findViewById(R.id.vendor_address);
            callVendorButton = (Button) itemView.findViewById(R.id.call_vendor_button);
            removeFromCartButton = (Button) itemView.findViewById(R.id.remove_from_cart);
        }
    }
}
