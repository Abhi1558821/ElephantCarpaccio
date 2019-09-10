package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.model.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CartPresenterTest {

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private CartContract.View view;

    private CartPresenter cartPresenter;
    @Mock
    private CartContract.CartInteractor cartInteractor;
    private Item itemA;
    private Item itemB;
    private List<Item> itemList;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cartPresenter = new CartPresenter(view, cartInteractor, appDatabase);
        itemList = new ArrayList<>();
        itemA = new Item();
        itemA.id = 1;
        itemA.setItemName("ITEM A");
        itemA.setItemQuantity(11);
        itemA.setItemUnitPrice(100);
        itemA.setState("Alabama");
        itemList.add(itemA);
        itemB = new Item();
        itemB.id = 2;
        itemB.setItemName("ITEM B");
        itemB.setItemQuantity(30);
        itemB.setItemUnitPrice(100);
        itemB.setState("Alabama");
        itemList.add(itemA);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void retrieveItemListTest() {
        cartPresenter.retrieveItemList();
        Mockito.verify(cartInteractor).retrieveItemList();
    }

    @Test
    public void openEditScreen() {
        cartPresenter.openEditScreen(itemA);
        Mockito.verify(view).showEditScreen(itemA.id);
    }

    @Test
    public void openConfirmDeleteDialog() {
        cartPresenter.openConfirmDeleteDialog(itemA);
        Mockito.verify(view).showDeleteConfirmDialog(itemA);
    }


    @Test
    public void delete() {
        cartPresenter.delete(itemA.id);
        Mockito.verify(cartInteractor).delete(itemA.id);
    }

    @Test
    public void getTotalPrice() {
        cartPresenter.getTotalPrice(itemList);
        Mockito.verify(cartInteractor).getTotalPrice(itemList);
    }

    @Test
    public void getTotalTaxValue() {
        cartPresenter.getTotalTaxValue(itemList);
        Mockito.verify(cartInteractor).getTotalTaxValue(itemList);
    }

    @Test
    public void getTaxValue() {
        cartPresenter.getTaxValue(itemA);
        Mockito.verify(cartInteractor).getTaxValue(itemA);
    }

    @Test
    public void getDiscountValue() {
        cartPresenter.getDiscountValue(10000.00);
        Mockito.verify(cartInteractor).getDiscountValue(10000.00);
    }

    @Test
    public void getPayableValue() {
        cartPresenter.getPayableValue(itemList);
        Mockito.verify(cartInteractor).getPayableValue(itemList);
    }

    @Test
    public void onItemListReceived() {
        cartPresenter.onItemListReceived(itemList);
        Mockito.verify(view).displayRecyclerView();
    }

    @Test
    public void onItemListReceivedWithEmptyList() {
        itemList.clear();
        cartPresenter.onItemListReceived(itemList);
        Mockito.verify(view).displayNoRecords();
    }
}