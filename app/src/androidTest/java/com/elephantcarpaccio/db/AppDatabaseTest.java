package com.elephantcarpaccio.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.db.dao.StateTaxDAO;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {
    private ItemDAO itemDAO;
    private StateTaxDAO stateTaxDAO;
    private AppDatabase appDb;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        itemDAO = appDb.itemModel();
        stateTaxDAO = appDb.stateTaxModel();
    }

    @After
    public void tearDown(){
        appDb.close();
    }

    @Test
    public void addItemAndGetListTest() {
        appDb.itemModel().deleteAll();

        List<Item> itemList = itemDAO.getAllItems();
        assertThat(itemList.size(), equalTo(0));

        addItem(appDb, 1, "Item A", 100.00, 100, "Alabama");
        addItem(appDb, 2, "Item B", 150.00, 50, "Florida");
        addItem(appDb, 3, "Item C", 200.00, 70, "Georgia");

        itemList = itemDAO.getAllItems();
        assertThat(itemList.size(), equalTo(3));
    }

    @Test
    public void updateAndDeleteItemTest() {
        appDb.itemModel().deleteAll();

        List<Item> itemList;
        addItem(appDb, 1, "Item A", 100.00, 100, "Alabama");
        addItem(appDb, 2, "Item B", 150.00, 50, "Florida");
        addItem(appDb, 3, "Item C", 200.00, 70, "Georgia");

        itemList = itemDAO.getAllItems();
        assertThat(itemList.size(), equalTo(3));

        itemDAO.deleteItem(itemList.get(0));
        itemList = itemDAO.getAllItems();
        assertThat(itemList.size(), equalTo(2));

        itemList.get(0).setItemName("Item Updated");
        itemDAO.updateItem(itemList.get(0));
        itemList = itemDAO.getAllItems();
        assertThat(itemList.get(0).getItemName(), equalTo("Item Updated"));
    }

    @Test
    public void getUpdateAndDeleteStateTest() {
        appDb.stateTaxModel().deleteAll();

        List<StateTax> stateTaxList;
        addStateTax(appDb, 1, "Alabama", 4.0);
        addStateTax(appDb, 2, "Florida", 0);
        addStateTax(appDb, 3, "Georgia", 4.0);

        stateTaxList = stateTaxDAO.getAllStateTax();
        assertThat(stateTaxList.size(), equalTo(3));

        stateTaxDAO.deleteStateTax(stateTaxList.get(0));
        stateTaxList = stateTaxDAO.getAllStateTax();
        assertThat(stateTaxList.size(), equalTo(2));

        stateTaxList.get(0).setTaxRate(5.30);
        stateTaxList.get(0).setState("Virginia");
        stateTaxDAO.updateStateTax(stateTaxList.get(0));
        stateTaxList = stateTaxDAO.getAllStateTax();
        assertThat(stateTaxList.get(0).getState(), equalTo("Virginia"));
        assertThat(stateTaxList.get(0).getTaxRate(), equalTo(5.30));
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

    private static void addStateTax(final AppDatabase db, final long id, final String stateName,
                                 final double taxRate) {
        StateTax stateTax = new StateTax();
        stateTax.id = id;
        stateTax.setState(stateName);
        stateTax.setTaxRate(taxRate);
        db.stateTaxModel().insertStateTax(stateTax);
    }
}
