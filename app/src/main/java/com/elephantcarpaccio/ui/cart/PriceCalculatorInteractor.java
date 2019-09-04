package com.elephantcarpaccio.ui.cart;

import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculatorInteractor implements CartContract.PriceCalculator {

    private AppDatabase database;

    public PriceCalculatorInteractor(AppDatabase appDatabase) {
        this.database = appDatabase;
    }

    @Override
    public double getTotalPrice(List<Item> itemList) {
        double totalPrice = 0;
        for (Item item : itemList) {
            totalPrice += getPrice(item);
        }
        return getFormattedDoubleValue(totalPrice);
    }

    @Override
    public double getPrice(Item item) {
        return item.getItemQuantity() * item.getItemUnitPrice();
    }

    @Override
    public double getTotalTaxValue(List<Item> itemList) {
        double totalTaxValue = 0;

        for (Item item : itemList) {
            totalTaxValue += getTaxValue(item);
        }
        return getFormattedDoubleValue(totalTaxValue);
    }

    @Override
    public double getTaxValue(Item item) {
        StateTax stateTax = database.stateTaxModel().getStateTaxFromState(item.getState());
        double taxValue;
        double taxRate = stateTax.getTaxRate();
        taxValue = (getPrice(item) * taxRate) / 100;
        return getFormattedDoubleValue(taxValue);
    }

    @Override
    public double getDiscountValue(double totalValue) {

        double discountValue = 0;

        float discountPercentage = getDiscountPercentage(totalValue);

        if (discountPercentage > 0) {
            discountValue = (totalValue * discountPercentage) / 100;
        }

        return getFormattedDoubleValue(discountValue);

    }

    @Override
    public float getDiscountPercentage(double totalPriceValue) {
        if (totalPriceValue > Constants.AMOUNT_FIFTY_THOUSAND) {
            return Constants.DISCOUNT_FIFTEEN_PERCENT;
        }

        if (totalPriceValue > Constants.AMOUNT_TEN_THOUSAND) {
            return Constants.DISCOUNT_TEN_PERCENT;
        }

        if (totalPriceValue > Constants.AMOUNT_SEVEN_THOUSAND) {
            return Constants.DISCOUNT_SEVEN_PERCENT;
        }

        if (totalPriceValue > Constants.AMOUNT_FIVE_THOUSAND) {
            return Constants.DISCOUNT_FIVE_PERCENT;
        }

        if (totalPriceValue > Constants.AMOUNT_ONE_THOUSAND) {
            return Constants.DISCOUNT_THREE_PERCENT;
        }

        return 0;
    }

    @Override
    public double getPayableValue(List<Item> itemList) {
        double totalPrice = 0;
        double totalTax = 0;

        for (Item item : itemList) {
            totalPrice += getPrice(item);
            totalTax += getTaxValue(item);
        }
        return getFormattedDoubleValue(totalPrice + totalTax - getDiscountValue(totalPrice));
    }

    /**
     * Method truncates the decimal places to 2 digits.
     *
     * @param value Get double value which needs to be truncated.
     * @return truncated double value.
     */
    private double getFormattedDoubleValue(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
