package com.example.mybusinesstracker.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardFragment;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;

public class DashboardActivity extends FactoryBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.dashboard_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DashboardFragment.newInstance())
                    .commitNow();
        }
    }
}
