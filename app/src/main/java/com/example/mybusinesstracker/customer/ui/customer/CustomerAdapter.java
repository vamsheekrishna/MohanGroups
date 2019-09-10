package com.example.mybusinesstracker.customer.ui.customer;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.customer.CustomerFragmentInteractionListener;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
    ArrayList<Customer> customerList;
    CustomerFragmentInteractionListener mListener;
    CustomerAdapter(ArrayList<Customer> customers, CustomerFragmentInteractionListener listener) {
        customerList = customers;
        mListener = listener;
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.customer_list_row, parent, false);
        return new CustomerViewHolder(listItem, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final Customer customer = customerList.get(position);
        holder.mRoot.setBackgroundColor(customer.getColorID());

        holder.mName.setText(customer.getCustomerName());

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor(String.valueOf(customer.getColorID()))});
        gd.setCornerRadius(0f);

        holder.mName.setBackground(gd);

//        holder.mName.setBackgroundColor(customer.getColorID());

        holder.mNumber.setBackgroundColor(customer.getColorID());
        holder.mNumber.setText(customer.getPhoneNumber());

        holder.mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToUpdateCustomer(customer);
                //Toast.makeText(view.getContext(),"click on item: "+customer.getCustomerName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}
