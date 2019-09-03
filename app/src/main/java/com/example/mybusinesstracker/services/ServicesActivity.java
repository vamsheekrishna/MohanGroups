package com.example.mybusinesstracker.services;


import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardFragment;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;

public class ServicesActivity extends FactoryBaseActivity implements OnServiceInteractionListener{


    //public ServiceListAdapter serviceListAdapter;
    public ServicesViewModel servicesViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ServiceHomeFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);


    }*/

    @Override
    protected void onStart() {
        super.onStart();
        //servicesViewModel.setUpData();
    }

    @Override
    public void gotToServiceFragment() {
        replaceFragment(ServiceFragment.newInstance(),"service_Fragment");
    }
}
