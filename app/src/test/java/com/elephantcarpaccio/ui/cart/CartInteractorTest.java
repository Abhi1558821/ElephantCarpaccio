package com.elephantcarpaccio.ui.cart;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.db.dao.StateTaxDAO;
import com.elephantcarpaccio.model.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class CartInteractorTest {
    @Mock
    private AppDatabase appDatabase;

    @Mock
    private CartPresenter cartPresenter;

    private CartContract.CartInteractor cartInteractor;

    private Item itemA;

    private Item itemB;

    private List<Item> itemList;

    @Mock
    private Context context;
    private ItemDAO itemDAO;
    private StateTaxDAO stateTaxDAO;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        itemDAO = appDatabase.itemModel();
        stateTaxDAO = appDatabase.stateTaxModel();
        cartInteractor = new CartInteractor();
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
    public void setDatabase() {
        /*appDatabase.itemModel().deleteAll();

        List<Item> itemList = itemDAO.getAllItems();
        assertThat(itemList.size(), equalTo(0));

        addItem(appDatabase, 1, "Item A", 100.00, 100, "Alabama");
        addItem(appDatabase, 2, "Item B", 150.00, 50, "Florida");
        addItem(appDatabase, 3, "Item C", 200.00, 70, "Georgia");

        itemList = itemDAO.getAllItems();
        assertThat(itemList.size(), equalTo(3));*/
    }

    @Test
    public void setPresenter() {
        cartInteractor.setPresenter(cartPresenter);
        Mockito.verify(cartInteractor).setPresenter(cartPresenter);
    }

    @Test
    public void getTotalTaxValue() {

    }

    @Test
    public void getTaxValue() {
        cartInteractor.getTaxValue(itemA);
    }

    @Test
    public void retrieveItemList() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getDiscountValue() {
    }

    @Test
    public void getDiscountPercentage() {
    }

    @Test
    public void getTotalPrice() {
    }

    @Test
    public void getPayableValue() {
    }

    private static void addItem(final AppDatabase db, final long id, final String itemName,
                                final double unitPrice, final int quantity, final String state) {
        Item item = new Item();
        item.id = id;
        item.setItemName(itemName);
        item.setItemUnitPrice(unitPrice);
        item.setItemQuantity(quantity);
        item.setState(state);
        db.itemModel().insertItem(item);
    }

}