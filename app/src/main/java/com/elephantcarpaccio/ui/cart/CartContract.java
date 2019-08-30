package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.model.Item;

import java.util.List;

public interface CartContract {

    interface View {
        /**
         * @param itemsList Displays collection of @{@link Item} objects to recycler view
         */
        void setItems(List<Item> itemsList);

        /**
         * If user cart is empty then display no records available view
         */
        void displayNoRecords();

        /**
         * If user cart contains items then display recycler view
         */
        void displayRecyclerView();
    }

    interface Presenter {
        /**
         * Set collection of {@link Item} objects to view
         */
        void setItemList();

        void clearItems();
    }

    interface PriceCalculator {

        /**
         * Method calculates the total price of all the {@link Item} objects without discount and tax.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A total price of all items
         */
        double getTotalPrice(List<Item> itemList);

        /**
         * Method calculates the total price of the {@link Item} object without discount and tax.
         *
         * @param item {@link Item} object.
         * @return A total price of item
         */
        double getPrice(Item item);

        /**
         * Method calculates the total tax of the {@link Item}.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A total tax price of all items.
         */
        double getTotalTaxValue(List<Item> itemList);

        /**
         * Method calculates the total tax of the {@link Item}.
         *
         * @param item {@link Item} object.
         * @return A total tax price of item.
         */
        double getTaxValue(Item item);

        /**
         * Method calculates the discount on the total price.
         *
         * @param totalValue total price.
         * @return A discount price.
         */
        double getDiscountValue(double totalValue);

        /**
         * Method calculates the discount percentage from the total price.
         *
         * @param totalPriceValue total price.
         * @return A discount percentage.
         */
        float getDiscountPercentage(double totalPriceValue);

        /**
         * Method calculates the total price of all the {@link Item} objects with discount and tax.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A final total price with discount and tax.
         */
        double getPayableValue(List<Item> itemList);
    }
}
