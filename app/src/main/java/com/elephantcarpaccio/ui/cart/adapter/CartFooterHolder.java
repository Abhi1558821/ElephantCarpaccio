package com.elephantcarpaccio.ui.cart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.ui.cart.CartContract;

import java.util.List;
import java.util.Locale;

class CartFooterHolder extends RecyclerView.ViewHolder {
    private TextView txtvwTotal;
    private TextView txtvwDiscount;
    private TextView txtvwTax;
    private TextView txtvwPayable;

    CartFooterHolder(View itemView) {
        super(itemView);
        this.txtvwTotal = itemView.findViewById(R.id.textview_total_value);
        this.txtvwDiscount = itemView.findViewById(R.id.textview_discount_value);
        this.txtvwTax = itemView.findViewById(R.id.textview_tax_value);
        this.txtvwPayable = itemView.findViewById(R.id.textview_total_payable_value);
    }

    void setData(List<Item> itemList, CartContract.Presenter cartCalculator) {
        double totalValue = cartCalculator.getTotalPrice(itemList);
        double discountValue = cartCalculator.getDiscountValue(totalValue);
        double taxValue = cartCalculator.getTotalTaxValue(itemList);
        double totalPayableValue = cartCalculator.getPayableValue(itemList);
        txtvwTotal.setText(String.format(Locale.US, "%.2f", totalValue));
        txtvwDiscount.setText(String.format("%s%s", itemView.getContext().getString(R.string.negative_value), String.format(Locale.US, "%.2f", discountValue)));
        txtvwTax.setText(String.format("%s%s", itemView.getContext().getString(R.string.plus_value), String.format(Locale.US, "%.2f", taxValue)));
        txtvwPayable.setText(String.format(Locale.US, "%.2f", totalPayableValue))
        ;
    }
}