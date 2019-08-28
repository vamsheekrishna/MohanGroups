package com.example.mybusinesstracker.dashboard.ui.dashboard;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;

import com.example.mybusinesstracker.cabin.IceBlock;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardViewModel extends BaseObservable implements Serializable {

    private Calendar selectedDate;

   /* String cabinName;
    int totalBlocks;
    int availableBlocks;

    int oneFourth;
    int threeFourth;
    int oneTwo;

    int todaySales;*/

    private ArrayList<SalesViewModel> salesViewModels = new ArrayList<>();
    private CabinViewModel cabinViewModel = new CabinViewModel();
    private SalesViewModel addNewSales = new SalesViewModel();
    void addSale(SalesViewModel salesViewModel) {
        salesViewModels.add(salesViewModel);
    }

    public CabinViewModel getCabinViewModel() {
        return cabinViewModel;
    }

    void setCabinViewModel(CabinViewModel viewModel) {
        this.cabinViewModel = viewModel;
    }

    public ArrayList<SalesViewModel> getSalesViewModels() {
        return salesViewModels;
    }

    public void setSalesViewModels(ArrayList<SalesViewModel> salesViewModels) {
        this.salesViewModels = salesViewModels;
    }

    public SalesViewModel getAddNewSales() {
        return addNewSales;
    }

    void setAddNewSales(SalesViewModel addNewSales) {
        this.addNewSales = addNewSales;
    }

    Calendar getSelectedDate() {
        return selectedDate;
    }

    void setSelectedDate(Calendar selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void addBricks(ArrayList<IceBlock> blocks) {

    }
}
