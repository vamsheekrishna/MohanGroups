package com.example.mybusinesstracker.cabin;

import android.os.Bundle;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinHomeFragment;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CreateFragment;
import com.example.mybusinesstracker.cabin.ui.cabinhome.OnCabinInteractionListener;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;

public class CabinActivity extends FactoryBaseActivity implements OnCabinInteractionListener {

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
}
