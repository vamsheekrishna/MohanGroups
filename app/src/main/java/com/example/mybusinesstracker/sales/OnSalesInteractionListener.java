package com.example.mybusinesstracker.sales;

import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesModel;
import com.example.mybusinesstracker.sales.ui.sales.TotalSalesInfo;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.Calendar;
import java.util.HashMap;

public interface OnSalesInteractionListener {
    HashMap<String, Customer> getCustomers();
    void onAddSaleRecordSuccess(SalesViewModel mViewModel);
    void onUpdateSaleRecordSuccess(SalesViewModel mSalesViewModel);
    void onDeleteSaleRecordSuccess(SalesViewModel mSalesViewModel);
    void gotToAddSaleFragment(SalesViewModel salesViewModel);
    void gotToGroupBasedSalesFragment(GroupBasedSalesModel groupBasedSalesModel);
    void getSalesListFromCloud(Calendar calendar);
    void goToDiscreteBasedSalesFragment(TotalSalesInfo totalSalesInfo, String day_sales);
    void setTitle(String name);
}
