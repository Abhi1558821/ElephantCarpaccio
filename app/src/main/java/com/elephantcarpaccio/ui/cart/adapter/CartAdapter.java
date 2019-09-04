package com.elephantcarpaccio.ui.cart.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.ui.cart.CartContract;

import java.util.List;

/**
 * Adapter class for cart representation
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private List<Item> itemList;
    private final CartContract.PriceCalculator cartCalculator;
    private CartContract.OnItemClickListener onItemClickListener;

    public CartAdapter(CartContract.PriceCalculator interactor, CartContract.OnItemClickListener onItemClickListener) {
        this.cartCalculator = interactor;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() + 2 : 0);//Add 2 more size to list for Header and Footer
    }

    @Override
    public int getItemViewType(int position) {
        //Return item type according to requirement
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;

    }

    //if position is 0 then type is header
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    //If position is last then type is footer
    private boolean isPositionFooter(int position) {
        return position == itemList.size() + 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //If instance is CartItemViewHolder the type is Item do your stuff over here
        if (CartItemViewHolder.class.isInstance(holder)) {

            ((CartItemViewHolder) holder).setData(itemList.get(position - 1), cartCalculator);
            holder.itemView.setOnClickListener(v -> onItemClickListener.clickItem(itemList.get(position - 1)));

            holder.itemView.setOnLongClickListener(v -> {
                onItemClickListener.clickLongItem(itemList.get(position - 1));
                return false;
            });
        } else if (CartHeaderHolder.class.isInstance(holder)) {

            ((CartHeaderHolder) holder).setData();

        } else {

            ((CartFooterHolder) holder).setData(itemList, cartCalculator);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        //inflate the item according to item type
        View itemView;
        //Since we are using same holder for both header and footer so we can return same holder
        if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_cart, viewGroup, false);
            return new CartHeaderHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_cart_footer, viewGroup, false);
            return new CartFooterHolder(itemView);
        } else if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_cart, viewGroup, false);
            return new CartItemViewHolder(itemView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");//Some error occurs then exception occurs

    }
}