package com.elephantcarpaccio.ui.cart.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elephantcarpaccio.R;

class CartHeaderHolder extends RecyclerView.ViewHolder {
    private TextView txtvwHeaderName;
    private TextView txtHeaderUnitPrice;
    private TextView txtvwHeaderQuantity;
    private TextView txtHeaderTotal;

    CartHeaderHolder(View itemView) {
        super(itemView);
        itemView.setClickable(false);
        this.txtvwHeaderName = itemView.findViewById(R.id.textview_name);
        this.txtHeaderUnitPrice = itemView.findViewById(R.id.textview_unit_price);
        this.txtvwHeaderQuantity = itemView.findViewById(R.id.textview_quantity);
        this.txtHeaderTotal = itemView.findViewById(R.id.textview_total);

        this.txtvwHeaderName.setTypeface(Typeface.DEFAULT_BOLD);
        this.txtHeaderUnitPrice.setTypeface(Typeface.DEFAULT_BOLD);
        this.txtvwHeaderQuantity.setTypeface(Typeface.DEFAULT_BOLD);
        this.txtHeaderTotal.setTypeface(Typeface.DEFAULT_BOLD);
    }

    void setData() {
        txtvwHeaderName.setText(itemView.getContext().getString(R.string.name));//if position is 0 set txtvwHeaderName to header view
        txtvwHeaderQuantity.setText(itemView.getContext().getString(R.string.quantity));
        txtHeaderUnitPrice.setText(itemView.getContext().getString(R.string.unit_price));
        txtHeaderTotal.setText(itemView.getContext().getString(R.string.total));
    }
}