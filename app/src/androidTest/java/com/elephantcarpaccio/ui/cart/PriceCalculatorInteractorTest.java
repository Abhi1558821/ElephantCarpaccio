package com.elephantcarpaccio.ui.cart;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.db.dao.StateTaxDAO;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PriceCalculatorInteractorTest {

    private String stateTaxJson;
    private List<Item> itemList;
    private Item itemA;
    private Item itemB;
    private CartContract.PriceCalculator priceCalculatorInteractor;

    private AppDatabase database;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        database = AppDatabase.getMemoryDatabase(context);
        ItemDAO itemDAO = database.itemModel();
        StateTaxDAO stateTaxDAO = database.stateTaxModel();
        itemList = new ArrayList<>();
        if (stateTaxJson == null) {
            stateTaxJson = "[{\"state\":\"Alabama\",\"tax\":\"4\"},{\"state\":\"Alaska\",\"tax\":\"0\"},{\"state\":\"Arizona\",\"tax\":\"5.60\"},{\"state\":\"Arkansas\",\"tax\":\"6.50\"},{\"state\":\"California\",\"tax\":\"7.25\"},{\"state\":\"Colorado\",\"tax\":\"2.90\"},{\"state\":\"Connecticut\",\"tax\":\"6.35\"},{\"state\":\"Delaware\",\"tax\":\"0\"},{\"state\":\"District of Columbia\",\"tax\":\"5.75\"},{\"state\":\"Florida\",\"tax\":\"6\"},{\"state\":\"Georgia\",\"tax\":\"4\"},{\"state\":\"Guam\",\"tax\":\"4\"},{\"state\":\"Hawaii\",\"tax\":\"4.17\"},{\"state\":\"Idaho\",\"tax\":\"6\"},{\"state\":\"Illinois\",\"tax\":\"6.25\"},{\"state\":\"Indiana\",\"tax\":\"7\"},{\"state\":\"lowa\",\"tax\":\"6\"},{\"state\":\"Kansas\",\"tax\":\"6.50\"},{\"state\":\"Kentucky\",\"tax\":\"6\"},{\"state\":\"Louisiana\",\"tax\":\"4.45\"},{\"state\":\"Maine\",\"tax\":\"5.50\"},{\"state\":\"Maryland\",\"tax\":\"6\"},{\"state\":\"Massachusetts\",\"tax\":\"6.25\"},{\"state\":\"Michigan\",\"tax\":\"6\"},{\"state\":\"Minnesota\",\"tax\":\"6.88\"},{\"state\":\"Mississippi\",\"tax\":\"7\"},{\"state\":\"Missouri\",\"tax\":\"4.23\"},{\"state\":\"Montana\",\"tax\":\"0\"},{\"state\":\"Nebraska\",\"tax\":\"5.50\"},{\"state\":\"Nevada\",\"tax\":\"6.85\"},{\"state\":\"New Hampshire\",\"tax\":\"0\"},{\"state\":\"New Jersey\",\"tax\":\"6.63\"},{\"state\":\"New Mexico\",\"tax\":\"5.13\"},{\"state\":\"New York\",\"tax\":\"4\"},{\"state\":\"North Carolina\",\"tax\":\"4.75\"},{\"state\":\"North Dakota\",\"tax\":\"5\"},{\"state\":\"Ohio[43]\",\"tax\":\"5.75\"},{\"state\":\"Oklahoma\",\"tax\":\"4.50\"},{\"state\":\"Oregon\",\"tax\":\"0\"},{\"state\":\"Pennsylvania\",\"tax\":\"6\"},{\"state\":\"Puerto Rico\",\"tax\":\"10.50\"},{\"state\":\"Rhode Island\",\"tax\":\"7\"},{\"state\":\"South Carolina\",\"tax\":\"6\"},{\"state\":\"South Dakota\",\"tax\":\"4\"},{\"state\":\"Tennessee\",\"tax\":\"7\"},{\"state\":\"Texas\",\"tax\":\"6.25\"},{\"state\":\"Utah\",\"tax\":\"5.95\"},{\"state\":\"Vermont\",\"tax\":\"6\"},{\"state\":\"Virginia\",\"tax\":\"5.30\"},{\"state\":\"Washington\",\"tax\":\"6.50\"},{\"state\":\"West Virginia\",\"tax\":\"6\"},{\"state\":\"Wisconsin\",\"tax\":\"5\"},{\"state\":\"Wyoming\",\"tax\":\"4\"}]";
        }
        priceCalculatorInteractor = new PriceCalculatorInteractor(AppDatabase.getMemoryDatabase(context));

        List<StateTax> stateTaxList = StateTax.createStateTaxList(stateTaxJson);
        stateTaxDAO.insertStateTaxList(stateTaxList);

        itemA = new Item();
        itemA.setItemName("ITEM A");
        itemA.setItemQuantity(11);
        itemA.setItemUnitPrice(100);
        itemA.setState("Alabama");
        itemList.add(itemA);
        itemDAO.insertItem(itemA);

        itemB = new Item();
        itemB.setItemName("ITEM B");
        itemB.setItemQuantity(30);
        itemB.setItemUnitPrice(100);
        itemB.setState("Alabama");
        itemList.add(itemB);
        itemDAO.insertItem(itemB);
    }

    @Test
    public void testCalculatePrice() {
        assertEquals("Total Price of Item B is different", 3000, priceCalculatorInteractor.getPrice(itemB), 0);
        assertEquals("Total Price of Item List is different", 4100, priceCalculatorInteractor.getTotalPrice(itemList), 0);
        assertEquals(4141, priceCalculatorInteractor.getPayableValue(itemList), 0);
    }

    @Test
    public void testCalculateDiscount() {
        assertEquals("Calculate discount percentage of ItemB", 3, priceCalculatorInteractor.getDiscountPercentage(priceCalculatorInteractor.getPrice(itemB)), 0);
        assertEquals("Calculate discount value of Item List", 123, priceCalculatorInteractor.getDiscountValue(priceCalculatorInteractor.getTotalPrice(itemList)), 0);
    }

    @Test
    public void testCalculatTax() {
        assertEquals("Calculate Tax Price of ItemA", 44, priceCalculatorInteractor.getTaxValue(itemA), 0);
        assertEquals("Calculate total Tax Value of Item List", 164, priceCalculatorInteractor.getTotalTaxValue(itemList), 0);
    }

    @Test
    public void testCalculatTotalPayablePrice() {
        assertEquals("Calculate total price with tax and discount", 4141, priceCalculatorInteractor.getPayableValue(itemList), 0);
    }

    @After
    public void tearDown() {
        stateTaxJson = null;
        itemList = null;
        database.close();
    }
}