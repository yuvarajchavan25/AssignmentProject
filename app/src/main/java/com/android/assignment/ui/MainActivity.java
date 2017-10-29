package com.android.assignment.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.assignment.R;
import com.android.assignment.ui.cart.CartFragment;
import com.android.assignment.ui.Product.ProductFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private TextView mTitle;
    private CartFragment cartFragment;
    private ProductFragment productFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBar();
        initView();
        setUpViewPager();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mTitle = (TextView) findViewById(R.id.title);
        mViewPager.addOnPageChangeListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        productFragment = new ProductFragment();
        cartFragment = new CartFragment();
        adapter.addFragment(productFragment);
        adapter.addFragment(cartFragment);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.product_menu:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.cart_menu:
                mViewPager.setCurrentItem(1);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
        setToolBarTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setToolBarTitle(int position) {
        if (position == 0)
            mTitle.setText(getResources().getString(R.string.shop));
        else
            mTitle.setText(getResources().getString(R.string.cart));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cartFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
