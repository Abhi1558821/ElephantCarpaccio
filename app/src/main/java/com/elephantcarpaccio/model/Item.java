package com.elephantcarpaccio.model;

/**
 * Model representation of a Item.
 */
public class Item {

    private String itemName;

    private double itemUnitPrice;

    private int itemQuantity;

    private StateTax stateTax;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public StateTax getStateTax() {
        return stateTax;
    }

    public void setStateTax(StateTax stateTax) {
        this.stateTax = stateTax;
    }
}
