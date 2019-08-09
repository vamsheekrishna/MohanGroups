package com.example.mybusinesstracker.sales.ui.sales;

import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class GroupBasedSalesModel {
    private Calendar calendar = Calendar.getInstance();
    TotalSalesInfo totalSalesInfo = new TotalSalesInfo();
    private ArrayList<SalesViewModel> allSales = new ArrayList<>();
    private HashMap<String, TotalSalesInfo> byNameSales = new HashMap<>();
    private HashMap<String, TotalSalesInfo> byDateSales = new HashMap<>();


    void clearAllData() {
        allSales.clear();
        byNameSales.clear();
        byDateSales.clear();
        totalSalesInfo.resetValues();
    }
    void addSale(SalesViewModel salesViewModel, String header, String subHeader, String format) {
        allSales.add(salesViewModel);
        totalSalesInfo.addSale(salesViewModel);
        if(!byNameSales.containsKey(salesViewModel.getCustomerID())) {
            TotalSalesInfo totalSalesInfo =  new TotalSalesInfo();
            totalSalesInfo.setHeaderText(header);
            totalSalesInfo.setHeaderSubText(subHeader);
            totalSalesInfo.setName(salesViewModel.getCustomerID());
            totalSalesInfo.addSale(salesViewModel);
            byNameSales.put(salesViewModel.getCustomerID(), totalSalesInfo);
        } else {
            Objects.requireNonNull(byNameSales.get(salesViewModel.getCustomerID())).addSale(salesViewModel);
        }
        String key = Utils.getStringFromDate(salesViewModel.getDate(), format);
        if(!byDateSales.containsKey(key)) {
            TotalSalesInfo totalSalesInfo =  new TotalSalesInfo();
            totalSalesInfo.setHeaderText(header);
            totalSalesInfo.setHeaderSubText(subHeader);
            totalSalesInfo.setName(key);
            totalSalesInfo.addSale(salesViewModel);
            //ArrayList<Integer> saleIDs = new ArrayList<>();
            //saleIDs.add(temp);
            byDateSales.put(key, totalSalesInfo);
        } else {
            Objects.requireNonNull(byDateSales.get(key)).addSale(salesViewModel);
        }
    }
    public SalesViewModel getSalesViewModel(int position) {
        return allSales.get(position);
    }
    HashMap<String, TotalSalesInfo> getSalesByName() {
        return byNameSales;
    }
    HashMap<String, TotalSalesInfo> getSalesByDate() {
        return byDateSales;
    }
    void setCalendar(Long date) {
        calendar.setTimeInMillis(date);
    }
    Calendar getCalendar() {
        return calendar;
    }
}
