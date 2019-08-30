package com.elephantcarpaccio.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.ui.cart.adapter.CartAdapter;
import com.elephantcarpaccio.ui.additem.AddItemActivity;
import com.elephantcarpaccio.utils.CartItemUtils;
import com.elephantcarpaccio.utils.Constants;

import java.util.List;


public class CartActivity extends AppCompatActivity implements CartContract.View {
    private RecyclerView rvCart;
    private FloatingActionButton fabAddItem;
    private CartContract.Presenter presenter;
    private TextView textViewNoRecords;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        presenter = new CartPresenter(this, CartItemUtils.getInstance());
        initialiseUI();
        presenter.setItemList();
    }

    // Initialise all UI components
    private void initialiseUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvCart = findViewById(R.id.rv_items);

        textViewNoRecords = findViewById(R.id.txtvw_no_records);

        fabAddItem = findViewById(R.id.fab_add_item);

        fabAddItem.setOnClickListener((View v) -> {
            Intent intent = new Intent(CartActivity.this, AddItemActivity.class);
            startActivityForResult(intent, Constants.ADD_USER_ITEM);
        });
    }

    @Override
    public void setItems(List<Item> itemsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecorationVertical = new DividerItemDecoration(rvCart.getContext(), DividerItemDecoration.VERTICAL);
        rvCart.addItemDecoration(dividerItemDecorationVertical);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setItemAnimator(new DefaultItemAnimator());
        cartAdapter = new CartAdapter(new PriceCalculatorInteractor(), itemsList);
        rvCart.setAdapter(cartAdapter);
    }

    @Override
    public void displayNoRecords() {
        rvCart.setVisibility(View.GONE);
        textViewNoRecords.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayRecyclerView() {
        rvCart.setVisibility(View.VISIBLE);
        textViewNoRecords.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.clearItems();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ADD_USER_ITEM) {
            if (resultCode == RESULT_OK) {
                presenter.setItemList();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.clearItems();
    }
}
