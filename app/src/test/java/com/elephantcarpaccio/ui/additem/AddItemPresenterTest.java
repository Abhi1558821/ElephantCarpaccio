package com.elephantcarpaccio.ui.additem;

import com.elephantcarpaccio.db.dao.ItemDAO;
import com.elephantcarpaccio.db.dao.StateTaxDAO;
import com.elephantcarpaccio.model.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class AddItemPresenterTest {

    private AddItemContract.Presenter userInpuPresenter;
    @Mock
    private AddItemContract.View view;

    @Mock
    private Item item;

    @Mock
    private ItemDAO itemDAO;

    @Mock
    private StateTaxDAO stateTaxDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userInpuPresenter = new AddItemPresenter(view, itemDAO, stateTaxDAO);
    }

    @After
    public void tearDown() {
        userInpuPresenter = null;
    }

    @Test
    public void saveItem() {
        userInpuPresenter.saveItem(item);
        Mockito.verify(itemDAO).insertItem(item);
        Mockito.verify(view).close();
    }

    @Test
    public void validateItemNameTest(){
        assertFalse(userInpuPresenter.validateItemName(""));
        assertTrue(userInpuPresenter.validateItemName("Item"));
    }

    @Test
    public void validateItemQuantityTest() {
        assertFalse(userInpuPresenter.validateItemQuantity(""));
        assertTrue(userInpuPresenter.validateItemQuantity("123"));
    }

    @Test
    public void validateUnitPriceTest() {
        assertFalse(userInpuPresenter.validateUnitPrice(""));
        assertTrue(userInpuPresenter.validateUnitPrice("12.12"));
    }

    @Test
    public void validateStateTest() {
        assertFalse(userInpuPresenter.validateState(""));
        assertTrue(userInpuPresenter.validateState("Alabama"));
    }

    @Test
    public void getAllStates(){
        userInpuPresenter.getAllStates();
        Mockito.verify(stateTaxDAO).getStatesList();
    }

    @Test
    public void retrieveItemTest() {
        Mockito.when(itemDAO.getItemFromId(1)).thenReturn(item);
        userInpuPresenter.retrieveItem(1);
        Mockito.verify(itemDAO).getItemFromId(1);
        Mockito.verify(view).displayUI(item);
    }

    @Test
    public void updateItem() {
        userInpuPresenter.updateItem(item);
        Mockito.verify(itemDAO).updateItem(item);
        Mockito.verify(view).close();
    }
}