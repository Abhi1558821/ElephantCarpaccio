package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.model.Item;

/**
 * Presenter implementation for CartActivity
 */
public class CartPresenter implements CartContract.Presenter {

    private CartContract.View view;
    private ItemDAO itemDAO;

    CartPresenter(CartContract.View view, ItemDAO itemDAO) {
        this.view = view;
        this.itemDAO = itemDAO;
    }

    @Override
    public void retrieveItemList() {
        itemDAO.getAllItem().observeForever(itemList -> {
            if (itemList == null || itemList.size() < 1) {
                view.displayNoRecords();
                return;
            }
            view.displayRecyclerView();
            view.setItems(itemList);
        });
    }

    @Override
    public void clearItems() {
        itemDAO.deleteAll();
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
        Item item = itemDAO.getItemFromId(itemId);
        itemDAO.deleteItem(item);
    }
}
