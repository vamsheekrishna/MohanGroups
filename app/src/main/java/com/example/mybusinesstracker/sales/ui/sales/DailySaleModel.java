package com.example.mybusinesstracker.sales.ui.sales;

import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class DailySaleModel {
    private Calendar calendar = Calendar.getInstance();
    public TotalSalesInfo totalSalesInfo = new TotalSalesInfo();
    private ArrayList<SalesViewModel> allSales = new ArrayList<>();
    //TotalSalesInfo byDateSales = new TotalSalesInfo();
    private HashMap<String, TotalSalesInfo> byNameSales = new HashMap<>();


    public void clearAllData() {
        allSales.clear();
        byNameSales.clear();
        totalSalesInfo = new TotalSalesInfo();
    }
    void addSale(SalesViewModel salesViewModel) {
        allSales.add(salesViewModel);
        totalSalesInfo.addSale(salesViewModel);
        if(!byNameSales.containsKey(salesViewModel.getCustomerID())) {
            TotalSalesInfo totalSalesInfo =  new TotalSalesInfo();
            totalSalesInfo.addSale(salesViewModel);
            //ArrayList<Integer> saleIDs = new ArrayList<>();
            //saleIDs.add(temp);
            byNameSales.put(salesViewModel.getCustomerID(), totalSalesInfo);
        } else {
            Objects.requireNonNull(byNameSales.get(salesViewModel.getCustomerID())).addSale(salesViewModel);
        }
    }
    public SalesViewModel getSalesViewModel(int position) {
        return allSales.get(position);
    }
    HashMap<String, TotalSalesInfo> getNameSales() {
        return byNameSales;
    }
    void setCalendar(Long date) {
        calendar.setTimeInMillis(date);
    }
    Calendar getCalendar() {
        return calendar;
    }
}
