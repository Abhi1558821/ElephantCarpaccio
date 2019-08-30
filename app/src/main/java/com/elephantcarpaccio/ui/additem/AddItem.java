package com.elephantcarpaccio.ui.additem;

import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.CartItemUtils;
import com.elephantcarpaccio.utils.Constants;

/**
 * Presenter implementation for AddItemActivity
 */
public class AddItem implements AddItemContract.Presenter {

    private final AddItemContract.View mView;

    private CartItemUtils cartItemUtils;

    public AddItem(AddItemContract.View mMainView, CartItemUtils cartItemUtils) {
        this.mView = mMainView;
        this.cartItemUtils = cartItemUtils;
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
    public boolean validateState(StateTax stateTax) {
        if (stateTax == null) {
            return false;
        }

        return !Constants.EMPTY.equalsIgnoreCase(stateTax.getState());
    }

    @Override
    public void saveItem(Item item) {
        cartItemUtils.addItem(item);
        mView.close();
    }

}
