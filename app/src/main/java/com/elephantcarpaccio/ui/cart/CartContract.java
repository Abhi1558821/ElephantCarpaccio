package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.db.AppDatabase;
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

        void showEditScreen(long id);

        void showDeleteConfirmDialog(Item item);

        /**
         * If user cart contains items then display recycler view
         */
        void displayRecyclerView();
    }

    interface Presenter {
        /**
         * Set collection of {@link Item} objects to view
         */
        void retrieveItemList();

        /**
         * Method used to edit the selected @{@link Item} object
         *
         * @param item selected @{@link Item} object which needs to edit
         */
        void openEditScreen(Item item);

        /**
         * Open confirmation dialog to delete the selected @{@link Item} object from database
         *
         * @param item the selected @{@link Item} object
         */
        void openConfirmDeleteDialog(Item item);

        /**
         * Delete the selected @{@link Item} object from database
         *
         * @param itemId the selected @{@link Item} object's itemId
         */
        void delete(long itemId);

        /**
         * Method calculates the total price of all the {@link Item} objects without discount and tax.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A total price of all items
         */
        double getTotalPrice(List<Item> itemList);

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
         * Method calculates the total price of all the {@link Item} objects with discount and tax.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A final total price with discount and tax.
         */
        double getPayableValue(List<Item> itemList);

        void onItemListReceived(List<Item> itemList);
    }

    interface OnItemClickListener {
        /**
         * Invoked this method when user clicks on any @{@link Item} from cart to
         * perform edit operation.
         *
         * @param item object selected by user
         */
        void clickItem(Item item);

        /**
         * Invoked this method when user long clicks on any @{@link Item} from cart to
         * perform delete operation.
         *
         * @param item object selected by user
         */
        void clickLongItem(Item item);
    }

    interface DeleteListener {
        /**
         * Method invoke when user agree to delete the item
         *
         * @param confirm boolean value to confirm the delete
         * @param itemId  of selected @{@link Item} to delete
         */
        void setConfirm(boolean confirm, long itemId);

    }

    interface CartInteractor {

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
         * Set collection of {@link Item} objects to view
         */
        void retrieveItemList();

        /**
         * Delete the selected @{@link Item} object from database
         *
         * @param itemId the selected @{@link Item} object's itemId
         */
        void delete(long itemId);

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
         * Method calculates the total price of all the {@link Item} objects without discount and tax.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A total price of all items
         */
        double getTotalPrice(List<Item> itemList);

        /**
         * Method calculates the total price of all the {@link Item} objects with discount and tax.
         *
         * @param itemList collection of {@link Item} objects.
         * @return A final total price with discount and tax.
         */
        double getPayableValue(List<Item> itemList);

        void setDatabase(AppDatabase appDatabase);

        void setPresenter(CartContract.Presenter presenter);
    }
}
