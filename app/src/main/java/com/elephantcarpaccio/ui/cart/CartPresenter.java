package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.model.Item;

import java.util.List;

/**
 * Presenter implementation for CartActivity
 */
public class CartPresenter implements CartContract.Presenter {

    private CartContract.View view;
    private CartContract.CartInteractor cartInteractor;

    CartPresenter(CartContract.View view, CartContract.CartInteractor cartInteractor, AppDatabase appDatabase) {
        this.view = view;
        this.cartInteractor = cartInteractor;
        this.cartInteractor.setDatabase(appDatabase);
        this.cartInteractor.setPresenter(this);
    }

    @Override
    public void retrieveItemList() {
        cartInteractor.retrieveItemList();
    }

    @Override
    public void openEditScreen(Item item) {
        view.showEditScreen(item.id);
    }

    @Override
    public void openConfirmDeleteDialog(Item item) {
        view.showDeleteConfirmDialog(item);
    }

    @Override
    public void delete(long itemId) {
        cartInteractor.delete(itemId);
    }

    @Override
    public double getTotalPrice(List<Item> itemList) {
        return cartInteractor.getTotalPrice(itemList);
    }

    @Override
    public double getTotalTaxValue(List<Item> itemList) {
        return cartInteractor.getTotalTaxValue(itemList);
    }

    @Override
    public double getTaxValue(Item item) {
        return cartInteractor.getTaxValue(item);
    }

    @Override
    public double getDiscountValue(double totalValue) {
        return cartInteractor.getDiscountValue(totalValue);
    }

    @Override
    public double getPayableValue(List<Item> itemList) {
        return cartInteractor.getPayableValue(itemList);
    }

    @Override
    public void onItemListReceived(List<Item> itemList) {
        if (itemList == null || itemList.size() < 1) {
            view.displayNoRecords();
            return;
        }
        view.displayRecyclerView();
        view.setItems(itemList);
    }
}
