package com.example.mybusinesstracker.dashboard.ui.dashboard;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;

import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardViewModel extends BaseObservable implements Serializable {
    Calendar date;
    String cabinName;
    int totalBlocks;
    int availableBlocks;
    int todaySales;
    int oneFourth;
    int threeFourth;
    int oneTwo;
    ArrayList<SalesViewModel> salesViewModels = new ArrayList<>();
    CabinViewModel mCabinViewModel = new CabinViewModel();

    void addSale(SalesViewModel salesViewModel) {
        salesViewModels.add(salesViewModel);
    }

}
