package com.elephantcarpaccio.utils;

import com.elephantcarpaccio.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to maintain the items added by user
 */
public class CartItemUtils {

    private static volatile CartItemUtils cartItemUtilsInstance;

    private static List<Item> userAddedItems;

    //private constructor.
    private CartItemUtils() {
    }

    public static CartItemUtils getInstance() {
        if (cartItemUtilsInstance == null) {
            synchronized (CartItemUtils.class) {
                if (cartItemUtilsInstance == null){
                    cartItemUtilsInstance = new CartItemUtils();
                    userAddedItems = new ArrayList<>();
                }
            }
        }
        return cartItemUtilsInstance;
    }

    public void addItem(Item item){
        userAddedItems.add(item);
    }

    public List<Item> getUserAddedItems(){
        return userAddedItems;
    }

    public void clearAllItems(){
        userAddedItems.clear();
    }


}
