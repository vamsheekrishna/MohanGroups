package com.example.mybusinesstracker.sales.ui.sales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;

import java.util.ArrayList;

public class DayRecycleViewAdapter extends RecyclerView.Adapter<DaySaleViewHolder> {
    private ArrayList<CustomerSaleModel> listOfCustomerSaleModel;
    private View.OnClickListener mOnItemClickListener;

    DayRecycleViewAdapter(ArrayList<CustomerSaleModel> saleModelHashMap, View.OnClickListener onItemClickListener) {
        listOfCustomerSaleModel = saleModelHashMap;
        mOnItemClickListener = onItemClickListener;
    }

    /*private void getCustomerSaleList(HashMap<String, CustomerSaleModel> saleModelHashMap) {

    }*/


    @NonNull
    @Override
    public DaySaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.include_row_item_recycleview_name, parent, false);
        DaySaleViewHolder daySaleViewHolder = new DaySaleViewHolder(listItem);;
        daySaleViewHolder.rowInfo.setOnClickListener(mOnItemClickListener);
        return daySaleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaySaleViewHolder holder, int position) {
        TotalSalesInfo temp = listOfCustomerSaleModel.get(position).getCustomerSale();
        holder.rowInfo.setTag(temp);
        holder.mCustomerName.setText(String.valueOf(temp.getHeaderText()));
        holder.mTotalBlocksString.setText(String.valueOf(temp.getTotalBlocksString()));
        holder.mTotalIceBlocks.setText(String.valueOf(temp.getTotalBlock()));
        holder.mTotalIceAmount.setText(String.valueOf(temp.getTotalICEAmount()));
        holder.mTotalLabourCharges.setText(String.valueOf(temp.getTotalLabourCharges()));
        holder.mTotalAmount.setText(String.valueOf(temp.getTotalAmount()));
        holder.mTotalPaid.setText(String.valueOf(temp.getTotalPaid()));
        holder.mTotalDue.setText(String.valueOf(temp.getTotalDue()));

    }

    @Override
    public int getItemCount() {
        return listOfCustomerSaleModel.size();
    }
}
