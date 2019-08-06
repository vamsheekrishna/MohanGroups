package com.example.mybusinesstracker.sales.ui.sales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.databinding.CustomerBaseSalesRowItemBinding;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerBaseSalesAdapter extends RecyclerView.Adapter<CustomerBaseSaleViewHolder> {
    private ArrayList<SalesViewModel> mSalesViewModels;
    private View.OnClickListener mOnItemClick;
    public CustomerBaseSalesAdapter(ArrayList<SalesViewModel> salesViewModels, View.OnClickListener onItemClick) {
        setmSalesViewModels(salesViewModels);
        mOnItemClick = onItemClick;
    }
    @NonNull
    @Override
    public CustomerBaseSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          CustomerBaseSalesRowItemBinding binding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()),
                R.layout.customer_base_sales_row_item, parent, false);
        CustomerBaseSaleViewHolder customerBaseSaleViewHolder = new CustomerBaseSaleViewHolder(binding);
        customerBaseSaleViewHolder.view.setOnClickListener(mOnItemClick);
        return customerBaseSaleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerBaseSaleViewHolder holder, int position) {
        SalesViewModel dataModel = mSalesViewModels.get(position);
        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return mSalesViewModels.size();
    }

    public ArrayList<SalesViewModel> getmSalesViewModels() {
        return mSalesViewModels;
    }

    public void setmSalesViewModels(ArrayList<SalesViewModel> mSalesViewModels) {
        this.mSalesViewModels = mSalesViewModels;
    }
}
