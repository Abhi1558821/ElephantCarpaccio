package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.utils.CartItemUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class CartPresenterTest {
    private CartContract.Presenter cartPresenter;
    @Mock
    private CartContract.View view;

    @Mock
    private CartItemUtils cartItemUtils;

    @Mock
    private List<Item> itemList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cartPresenter = new CartPresenter(view, cartItemUtils);
    }

    @Test
    public void setItemListWithNoItemsTest() {
        Mockito.when(cartItemUtils.getUserAddedItems()).thenReturn(itemList);
        Mockito.when(itemList.size()).thenReturn(0);
        Mockito.when(itemList.isEmpty()).thenReturn(true);
        cartPresenter.setItemList();
//        Mockito.verify(cartItemUtils).getUserAddedItems();
        Mockito.verify(view).displayNoRecords();
    }

    @Test
    public void setItemListWIthItemsTest() {
        Mockito.when(cartItemUtils.getUserAddedItems()).thenReturn(itemList);
        Mockito.when(itemList.size()).thenReturn(2);

        cartPresenter.setItemList();
//        Mockito.verify(cartItemUtils, Mockito.atLeast(2)).getUserAddedItems();
        Mockito.verify(view).displayRecyclerView();
        Mockito.verify(view).setItems(itemList);
    }

    @Test
    public void clearItemsTest() {
        Mockito.when(cartItemUtils.getUserAddedItems()).thenReturn(itemList);
        Mockito.when(itemList.size()).thenReturn(1);
        cartPresenter.clearItems();
        Mockito.verify(cartItemUtils).getUserAddedItems();
        Mockito.verify(cartItemUtils).clearAllItems();
    }

    @After
    public void tearDown() {
        cartPresenter = null;
    }

}