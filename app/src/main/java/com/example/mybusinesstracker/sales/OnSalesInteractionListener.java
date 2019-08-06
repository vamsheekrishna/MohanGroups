package com.example.mybusinesstracker.sales;

import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.sales.ui.sales.CustomerSaleModel;
import com.example.mybusinesstracker.sales.ui.sales.TotalSalesInfo;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.Calendar;
import java.util.HashMap;

public interface OnSalesInteractionListener {
    HashMap<String, Customer> getCustomers();
    void onAddSaleRecordSuccess(SalesViewModel mViewModel);
    void onUpdateSaleRecordSuccess(SalesViewModel mSalesViewModel);
    void onDeleteSaleRecordSuccess(SalesViewModel mSalesViewModel);

    HashMap<Long, SalesViewModel> getDaySales();

    void gotToAddSaleFragment(SalesViewModel salesViewModel);
    void gotToMonthlyFragment();

    void getSalesListFromCloud(Calendar calendar);
    CustomerSaleModel getCustomerSales(String customerID);
    void goToCustomerBasedSalesFragment(String name, String date);
    void goToDiscreteBasedSalesFragment(TotalSalesInfo totalSalesInfo, String day_sales);
    void setTitle(String name);
}
