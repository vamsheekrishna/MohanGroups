package com.example.mybusinesstracker.customer.ui.customer;

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
        return new CustomerViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final Customer customer = customerList.get(position);
        holder.mName.setText(customer.getCustomerName());
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
