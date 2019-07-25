package com.example.mybusinesstracker.sales;

import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.sales.ui.sales.CustomerSaleModel;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public interface OnSalesInteractionListener {
    HashMap<String, Customer> getCustomers();
    void onAddSaleRecordSuccess(SalesViewModel mViewModel);
    void onUpdateSaleRecordSuccess();
    void onDeleteSaleRecordSuccess();

    HashMap<Long, SalesViewModel> getDaySales();

    void gotToAddSaleFragment();

    ArrayList<CustomerSaleModel> getSalesList();
    void getAllSalesList(Calendar calendar);
}
