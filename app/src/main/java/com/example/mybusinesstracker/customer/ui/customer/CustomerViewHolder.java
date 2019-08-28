package com.example.mybusinesstracker.customer.ui.customer;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public View mRoot;
    TextView mName;
    TextView mNumber;
    LinearLayout mCustomer;
    Button mCall, mMessage;
    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);
        mRoot = itemView;
        mName = itemView.findViewById(R.id.customer_name);
        mNumber = itemView.findViewById(R.id.customer_number);
        mCustomer = itemView.findViewById(R.id.customer_details);
        mCustomer.setOnClickListener(this);
        itemView.findViewById(R.id.call).setOnClickListener(this);
        itemView.findViewById(R.id.msg).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
                break;
            case R.id.msg:
                break;
            case R.id.customer_details:

                break;
        }
    }
}
