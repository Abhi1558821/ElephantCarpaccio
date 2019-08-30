package com.elephantcarpaccio.ui.additem;

import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;

public interface AddItemContract {

    interface View {
        void close();
    }

    interface Presenter {

        /**
         * Method validates the name.
         *
         * @param itemName name of item entered by user.
         * @return A boolean value if itemName is proper.
         */
        boolean validateItemName(String itemName);

        /**
         * Method validates the quantity.
         *
         * @param quantity of item entered by user .
         * @return A boolean value if quantity is proper.
         */
        boolean validateItemQuantity(String quantity);

        /**
         * Method validates the unit price.
         *
         * @param unitPrice of item entered by user .
         * @return A boolean value if unitPrice is proper.
         */
        boolean validateUnitPrice(String unitPrice);

        /**
         * Method validates the State {@link Item} object.
         *
         * @param  stateTax selected by user
         * @return A boolean value if {@link StateTax} is proper.
         */
        boolean validateState(StateTax stateTax);

        /**
         * Method save the {@link Item} object.
         *
         * @param item create {@link Item} object from user input and save.
         */
        void saveItem(Item item);

    }
}
