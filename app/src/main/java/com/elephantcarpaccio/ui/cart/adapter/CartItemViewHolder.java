package com.elephantcarpaccio.ui.cart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.ui.cart.CartContract;

import java.util.Locale;

class CartItemViewHolder extends RecyclerView.ViewHolder {
    private TextView txtvwName;
    private TextView txtvwUnitPrice;
    private TextView txtvwQuantity;
    private TextView txtvwTotal;

    CartItemViewHolder(View view) {
        super(view);
        this.txtvwName = view.findViewById(R.id.textview_name);
        this.txtvwUnitPrice = view.findViewById(R.id.textview_unit_price);
        this.txtvwQuantity = view.findViewById(R.id.textview_quantity);
        this.txtvwTotal = view.findViewById(R.id.textview_total);
    }

    void setData(Item item, CartContract.PriceCalculator cartCalculator) {
        txtvwName.setText(item.getItemName());//For getting data from array use -1 for getting correct data
        txtvwQuantity.setText(String.valueOf(item.getItemQuantity()));
        txtvwUnitPrice.setText(String.format(Locale.US, "%.2f", item.getItemUnitPrice()));
        txtvwTotal.setText(String.format(Locale.US, "%.2f", cartCalculator.getPrice(item)));
    }
}