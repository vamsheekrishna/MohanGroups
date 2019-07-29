package com.example.mybusinesstracker.sales.ui.sales;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;

public class DaySaleViewHolder extends RecyclerView.ViewHolder {
    TextView mCustomerName;
    TextView mTotalBlocksString;
    TextView mTotalIceBlocks;
    TextView mTotalIceAmount;
    TextView mTotalLabourCharges;
    TextView mTotalAmount;
    TextView mTotalPaid;
    TextView mTotalDue;
    LinearLayout rowInfo;
    DaySaleViewHolder(@NonNull View itemView) {
        super(itemView);
        rowInfo = itemView.findViewById(R.id.row_info);
        mCustomerName = itemView.findViewById(R.id.customer_name);
        mTotalBlocksString = itemView.findViewById(R.id.blocks);
        mTotalBlocksString.setVisibility(View.VISIBLE);
        mTotalIceBlocks = itemView.findViewById(R.id.total_blocks);
        mTotalIceAmount = itemView.findViewById(R.id.ice_amount);
        mTotalLabourCharges = itemView.findViewById(R.id.labour_cost);
        mTotalAmount = itemView.findViewById(R.id.total_amount);
        mTotalPaid = itemView.findViewById(R.id.paid_amount);
        mTotalDue = itemView.findViewById(R.id.due_amount);
    }
}
