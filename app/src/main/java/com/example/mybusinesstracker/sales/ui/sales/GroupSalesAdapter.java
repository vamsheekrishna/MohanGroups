package com.example.mybusinesstracker.sales.ui.sales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.databinding.GroupSalesRowItemBinding;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GroupSalesAdapter extends RecyclerView.Adapter<GroupBasedSalesViewHolder> {
    private ArrayList<TotalSalesInfo> mSalesViewModels;
    private View.OnClickListener mOnItemClick;
    public GroupSalesAdapter(HashMap<String, TotalSalesInfo> salesViewModels, View.OnClickListener onItemClick) {
        setSalesViewModels(salesViewModels.values());
        mOnItemClick = onItemClick;
    }
    @NonNull
    @Override
    public GroupBasedSalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          GroupSalesRowItemBinding binding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()),
                R.layout.group_sales_row_item, parent, false);
        GroupBasedSalesViewHolder customerTotalSaleViewHolder = new GroupBasedSalesViewHolder(binding);
        customerTotalSaleViewHolder.view.setOnClickListener(mOnItemClick);
        return customerTotalSaleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupBasedSalesViewHolder holder, int position) {
        TotalSalesInfo dataModel = mSalesViewModels.get(position);
        holder.view.setTag(dataModel);
        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return getSalesViewModels().size();
    }

    private ArrayList<TotalSalesInfo> getSalesViewModels() {
        return mSalesViewModels;
    }

    public void setSalesViewModels(Collection<TotalSalesInfo> mSalesViewModels) {
        this.mSalesViewModels = new ArrayList<>(mSalesViewModels);
    }
}
