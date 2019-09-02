package com.example.mybusinesstracker.cabin.ui.cabinhome;

import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardViewModel;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

public interface OnCabinInteractionListener {

    void goToCreteCabin(CabinViewModel cabinViewModel);

    void gotoSalesActivity(DashboardViewModel salesViewModel);
    void onAddSaleRecordSuccess(SalesViewModel mViewModel);
}
