package com.example.mybusinesstracker.sales.ui.sales;

import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class GroupBasedSalesModel implements Serializable {
    private Calendar calendar = Calendar.getInstance();
    TotalSalesInfo totalSalesInfo = new TotalSalesInfo();
    private Content content = Content.date;
    private ArrayList<SalesViewModel> allSales = new ArrayList<>();
    private HashMap<String, TotalSalesInfo> byNameSales = new HashMap<>();
    private HashMap<String, TotalSalesInfo> byDateSales = new HashMap<>();
    private Date dateType = Date.day;//0 -> day, 1 -> month, 2 -> year

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
    void setContentTypeName(boolean isContentTypeName) {
        if(isContentTypeName)
            content = Content.date;
        else
            content = Content.name;
    }
    Content getContentType() {
        return content;
    }
    void setCalendar(Long date) {
        calendar.setTimeInMillis(date);
    }
    Calendar getCalendar() {
        return calendar;
    }

    HashMap<String, TotalSalesInfo> getSalesData() {
        if(content == Content.name) {
            return getSalesByName();
        } else {
            return getSalesByDate();
        }
    }

    void setDateType(Date type) {
        dateType = type;
    }

    Date getDateType() {
        return dateType;
    }
    enum Content {
        name, date, month;
    }
    enum Date {
        day, month, year;
    }
}
