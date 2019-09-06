package com.elephantcarpaccio.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.ui.cart.adapter.CartAdapter;
import com.elephantcarpaccio.ui.additem.AddItemActivity;
import com.elephantcarpaccio.utils.Constants;

import java.util.List;


public class CartActivity extends AppCompatActivity implements CartContract.View, CartContract.OnItemClickListener, CartContract.DeleteListener {
    private RecyclerView rvCart;
    private CartContract.Presenter presenter;
    private TextView textViewNoRecords;
    private CartAdapter cartAdapter;
    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = AppDatabase.getDatabase(getApplication());

        presenter = new CartPresenter(this, database.itemModel());

        initialiseUI();
    }

    // Initialise all UI components
    private void initialiseUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvCart = findViewById(R.id.rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setHasFixedSize(true);
        rvCart.setItemAnimator(new DefaultItemAnimator());
        cartAdapter = new CartAdapter(new PriceCalculatorInteractor(database), this);
        rvCart.setAdapter(cartAdapter);

        textViewNoRecords = findViewById(R.id.txtvw_no_records);

        FloatingActionButton fabAddItem = findViewById(R.id.fab_add_item);

        fabAddItem.setOnClickListener((View v) -> {
            Intent intent = new Intent(CartActivity.this, AddItemActivity.class);
            startActivityForResult(intent, Constants.ADD_USER_ITEM);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.retrieveItemList();
    }

    @Override
    public void setItems(List<Item> itemsList) {
        cartAdapter.setItemList(itemsList);
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayNoRecords() {
        rvCart.setVisibility(View.GONE);
        textViewNoRecords.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditScreen(long id) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra(Constants.ITEM_ID, id);
        startActivity(intent);
    }

    @Override
    public void showDeleteConfirmDialog(Item item) {
        DeleteItemConfirmDialogFragment fragment = new DeleteItemConfirmDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.ITEM_ID, item.id);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "confirmDialog");
    }

    @Override
    public void displayRecyclerView() {
        rvCart.setVisibility(View.VISIBLE);
        textViewNoRecords.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ADD_USER_ITEM) {
            if (resultCode == RESULT_OK) {
                presenter.retrieveItemList();
            }
        }
    }

    @Override
    public void clickItem(Item item) {
        presenter.openEditScreen(item);
    }

    @Override
    public void clickLongItem(Item item) {
        presenter.openConfirmDeleteDialog(item);
    }

    @Override
    public void setConfirm(boolean confirm, long itemId) {
        if (confirm) {
            presenter.delete(itemId);
        }
    }
}



