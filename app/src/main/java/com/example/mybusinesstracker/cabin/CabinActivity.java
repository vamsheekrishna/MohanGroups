package com.example.mybusinesstracker.cabin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinHomeFragment;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CreteFragment;
import com.example.mybusinesstracker.cabin.ui.cabinhome.OnCabinInteractionListener;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;

public class CabinActivity extends FactoryBaseActivity implements OnCabinInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabin_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CabinHomeFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void goToCreteCabin() {
        replaceFragment(CreteFragment.newInstance(),"CreteFragment");
    }
}
