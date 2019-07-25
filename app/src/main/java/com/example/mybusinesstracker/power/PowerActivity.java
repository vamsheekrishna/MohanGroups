package com.example.mybusinesstracker.power;

import android.os.Bundle;

import com.example.mybusinesstracker.BaseCalsses.BaseActivity;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.power.ui.power.PowerFragment;

public class PowerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.power_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PowerFragment.newInstance())
                    .commitNow();
        }
    }
}
