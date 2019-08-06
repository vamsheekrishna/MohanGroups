package com.example.mybusinesstracker.sales.ui.sales;


import android.view.View;

import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.databinding.DiscreteBaseSalesRowItemBinding;


class DiscreteBaseSaleViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public DiscreteBaseSalesRowItemBinding mItemView;
    public DiscreteBaseSaleViewHolder(DiscreteBaseSalesRowItemBinding itemView) {
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
