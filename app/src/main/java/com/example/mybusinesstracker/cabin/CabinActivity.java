package com.example.mybusinesstracker.cabin;

import android.os.Bundle;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinHomeFragment;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CreateFragment;
import com.example.mybusinesstracker.cabin.ui.cabinhome.OnCabinInteractionListener;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;
import com.example.mybusinesstracker.sales.ui.sales.AddSaleFragment;

public class CabinActivity extends FactoryBaseActivity implements OnCabinInteractionListener {

    private Object SalesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CabinHomeFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void goToCreteCabin(CabinViewModel cabinViewModel) {
        replaceFragment(CreateFragment.newInstance(cabinViewModel),"CreateFragment");
    }

    @Override
    public void gotoSalesActivity(com.example.mybusinesstracker.viewmodels.SalesViewModel salesViewModel) {
        replaceFragment(AddSaleFragment.newInstance(salesViewModel),"AddSaleFragment");
    }
}
