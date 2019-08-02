package com.example.mybusinesstracker.sales.ui.sales;

import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DailySaleModel {
    public TotalSalesInfo totalSalesInfo = new TotalSalesInfo();
    private ArrayList<SalesViewModel> allSales = new ArrayList<>();
    private HashMap<Long, SalesViewModel> byDateSales = new HashMap<>();
    private HashMap<String, SalesViewModel> byNameSales = new HashMap<>();

    public void addSale(SalesViewModel salesViewModel) {
        allSales.add(salesViewModel);
    }
    public SalesViewModel getSalesViewModel(int position) {
        return allSales.get(position);
    }
}
