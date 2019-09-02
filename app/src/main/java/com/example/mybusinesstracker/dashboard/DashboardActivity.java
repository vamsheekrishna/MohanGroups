package com.example.mybusinesstracker.dashboard;

import android.os.Bundle;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cabin.ui.cabinhome.OnCabinInteractionListener;
import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardFragment;
import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardViewModel;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;
import com.example.mybusinesstracker.sales.ui.sales.AddSaleFragment;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

public class DashboardActivity extends FactoryBaseActivity implements OnCabinInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DashboardFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void goToCreteCabin(CabinViewModel cabinViewModel) {

    }

    @Override
    public void gotoSalesActivity(DashboardViewModel dashboardViewModel) {
        replaceFragment(AddSaleFragment.newInstance(dashboardViewModel, true),"AddSaleFragment");
    }

    @Override
    public void onAddSaleRecordSuccess(SalesViewModel mViewModel) {
        onBackPressed();
    }
}
