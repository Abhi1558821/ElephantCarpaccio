package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.CartItemUtils;

/**
 * Presenter implementation for CartActivity
 */
public class CartPresenter implements CartContract.Presenter {

    private CartContract.View view;
    private CartItemUtils cartItemUtils;

    public CartPresenter(CartContract.View view, CartItemUtils cartItemUtils) {
        this.view = view;
        this.cartItemUtils = cartItemUtils;
    }

    @Override
    public void setItemList() {
        if (!cartItemUtils.getUserAddedItems().isEmpty()) {
            view.displayRecyclerView();
            view.setItems(cartItemUtils.getUserAddedItems());
        } else {
            view.displayNoRecords();
        }

    }

    @Override
    public void clearItems() {
        if (!cartItemUtils.getUserAddedItems().isEmpty()) {
            cartItemUtils.clearAllItems();
        }
    }
}
