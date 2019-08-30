package com.elephantcarpaccio.ui.cart.adapter;

import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.ui.cart.CartContract;
import com.elephantcarpaccio.ui.cart.PriceCalculatorInteractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CartAdapterTest {

    private List<Item> itemList;

    private CartAdapter cartAdapter;

    private CartContract.PriceCalculator presenter;

    @Before
    public void setUp() {
        itemList = new ArrayList<>();

        Item item = new Item();
        item.setItemName("ABC");
        item.setItemQuantity(10);
        item.setItemUnitPrice(100.00f);
        item.setStateTax(new StateTax());

        itemList.add(item);
        presenter = new PriceCalculatorInteractor();
        cartAdapter = new CartAdapter(presenter, itemList);
    }

    @After
    public void tearDown() {
        itemList = null;
    }

    @Test
    public void getItemCount() {
        assertEquals("Items list size didn't match", 3, cartAdapter.getItemCount());
    }

    @Test
    public void getItemViewType() {
        assertEquals("View type didn't match", 0, cartAdapter.getItemViewType(0));
        assertEquals("View type didn't match", 1, cartAdapter.getItemViewType(2));

    }
}