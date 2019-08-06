package com.example.mybusinesstracker.sales.ui.sales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.databinding.DiscreteBaseSalesRowItemBinding;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;

public class DiscreteBaseSalesAdapter extends RecyclerView.Adapter<DiscreteBaseSaleViewHolder> {
    private ArrayList<SalesViewModel> mSalesViewModels;
    private View.OnClickListener mOnItemClick;
    public DiscreteBaseSalesAdapter(ArrayList<SalesViewModel> salesViewModels, View.OnClickListener onItemClick) {
        setSalesViewModels(salesViewModels);
        mOnItemClick = onItemClick;
    }
    @NonNull
    @Override
    public DiscreteBaseSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          DiscreteBaseSalesRowItemBinding binding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()),
                R.layout.discrete_base_sales_row_item, parent, false);
        DiscreteBaseSaleViewHolder discreteBaseSaleViewHolder = new DiscreteBaseSaleViewHolder(binding);
        discreteBaseSaleViewHolder.view.setOnClickListener(mOnItemClick);
        return discreteBaseSaleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscreteBaseSaleViewHolder holder, int position) {
        SalesViewModel dataModel = mSalesViewModels.get(position);
        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return mSalesViewModels.size();
    }

    public ArrayList<SalesViewModel> getSalesViewModels() {
        return mSalesViewModels;
    }

    public void setSalesViewModels(ArrayList<SalesViewModel> salesViewModels) {
        this.mSalesViewModels = salesViewModels;
    }
}
