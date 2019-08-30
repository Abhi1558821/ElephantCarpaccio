package com.elephantcarpaccio.ui.additem;

import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.CartItemUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class AddItemTest {

    private AddItemContract.Presenter userInpuPresenter;
    @Mock
    private AddItemContract.View view;

    @Mock
    private CartItemUtils cartItemUtils;

    @Mock
    private Item item;

    @Mock
    private StateTax stateTax;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userInpuPresenter = new AddItem(view, cartItemUtils);
    }

    @After
    public void tearDown() {
        userInpuPresenter = null;
    }

    @Test
    public void saveItem() {
        userInpuPresenter.saveItem(item);
        Mockito.verify(view).close();
        Mockito.verify(cartItemUtils).addItem(item);
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
        assertFalse(userInpuPresenter.validateState(null));
        assertTrue(userInpuPresenter.validateState(stateTax));
    }
}