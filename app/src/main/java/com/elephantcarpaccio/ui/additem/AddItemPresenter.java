package com.elephantcarpaccio.ui.additem;

import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.db.dao.StateTaxDAO;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.utils.Constants;

import java.util.List;

/**
 * Presenter implementation for AddItemActivity
 */
public class AddItemPresenter implements AddItemContract.Presenter {

    private final AddItemContract.View view;

    private ItemDAO itemDAO;

    private StateTaxDAO stateTaxDAO;

    AddItemPresenter(AddItemContract.View mMainView, ItemDAO itemDAO, StateTaxDAO stateTaxDAO) {
        this.view = mMainView;
        this.itemDAO = itemDAO;
        this.stateTaxDAO = stateTaxDAO;
    }

    @Override
    public boolean validateItemName(String itemName) {
        return !Constants.EMPTY.equalsIgnoreCase(itemName);
    }

    @Override
    public boolean validateItemQuantity(String quantity) {
        if (Constants.EMPTY.equalsIgnoreCase(quantity)) {
            return false;
        }
        return Integer.valueOf(quantity) >= 1;
    }

    @Override
    public boolean validateUnitPrice(String unitPrice) {
        if (Constants.EMPTY.equalsIgnoreCase(unitPrice)) {
            return false;
        }
        return Double.valueOf(unitPrice) > 0;
    }

    @Override
    public boolean validateState(String state) {
        return !Constants.EMPTY.equalsIgnoreCase(state);
    }

    @Override
    public void saveItem(Item item) {
//        cartItemUtils.addItem(item);
        itemDAO.insertItem(item);
        view.close();
    }

    @Override
    public List<String> getAllStates() {
        return stateTaxDAO.getStatesList();
    }

    @Override
    public void retrieveItem(long id) {
        Item item = itemDAO.getItemFromId(id);
        if (item != null) {
            view.displayUI(item);
        }
    }

    @Override
    public void updateItem(Item item) {
        itemDAO.updateItem(item);
        view.close();
    }

}
