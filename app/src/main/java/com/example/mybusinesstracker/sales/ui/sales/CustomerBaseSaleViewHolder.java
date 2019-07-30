package com.example.mybusinesstracker.sales.ui.sales;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.BR;
import com.example.mybusinesstracker.databinding.CustomerBaseSalesRowItemBinding;

class CustomerBaseSaleViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public CustomerBaseSalesRowItemBinding mItemView;
    public CustomerBaseSaleViewHolder(CustomerBaseSalesRowItemBinding itemView) {
        super(itemView.getRoot());
        view = itemView.getRoot();
        mItemView = itemView;
    }
    public void bind(Object obj) {
        mItemView.setVariable(BR.sale, obj);
        view.setTag(obj);
        //mItemView.setVariable(BR.sale, obj );
    }
}
