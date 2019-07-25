package com.example.mybusinesstracker.BaseCalsses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.customer.CustomerActivity;
import com.example.mybusinesstracker.power.PowerActivity;
import com.example.mybusinesstracker.sales.SalesActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity implements OnBaseAppListener {


    protected ActionBarDrawerToggle t;
    protected DrawerLayout dl;

    protected static String TAG ="BaseActivity";
    boolean isWifiConnected = false;
    boolean isMobileDataConnected = false;
    NavigationView nv;

    /*
     * setTagName() method is usefull in finding out which activity or module has raised a particular log accross the whole application
     */
    public void setTagName(String name){
        String classpath[] = name.split("\\.");
        TAG = classpath[classpath.length -1];
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
    }

    protected void setNavigationDrawer() {
        dl = findViewById(R.id.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Boolean x = goToActivity(id);
                if (x != null) return x;

                dl.closeDrawer(nv);
                return true;
            }
        });
    }

    private Boolean goToActivity(int id) {
        Intent intent;
        switch(id)
        {
            case R.id.current_bill:
                if (!(BaseActivity.this instanceof PowerActivity)) {
                    intent = new Intent(this, PowerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(BaseActivity.this, "Power Activity",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sales:
                if(!(BaseActivity.this instanceof SalesActivity)) {
                    intent = new Intent(this, SalesActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(BaseActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                }
            case R.id.customer:
                if(!(BaseActivity.this instanceof CustomerActivity)) {
                    intent = new Intent(this, CustomerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(BaseActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                }
            default:
                return true;
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isWifiConnected = false;
        isMobileDataConnected = false;
        unregisterReceiver(networkChangeReceiver);
    }

    BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetworkConnection();
        }
    };


    @Override
    public void onNetworkConnected() {
        //TODO: Perform any tasks when the network is connected.
    }

    @Override
    public void onNetworkChanging() {
        //TODO: handle network resources while switching network.
    }

    @Override
    public void onNetworkDisConnected() {
        //TODO: perform any data saving tasks.
    }

    @Override
    public void checkNetworkConnection() {
        isMobileDataConnected = false;
        isWifiConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        if (networkInfos != null){
            for (NetworkInfo ni: networkInfos) {
                if (ni.getState() == NetworkInfo.State.CONNECTED){
                    if (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected()){
                        isWifiConnected = true;
                    }
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected()){
                        isMobileDataConnected = true;
                    }
                }
            }
        }

        if (isWifiConnected || isMobileDataConnected)
            onNetworkConnected();
        else
            onNetworkDisConnected();
    }

    @Override
    public boolean isNetworkAvailable() {
        return isMobileDataConnected || isWifiConnected;
    }

    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Network not Found");
        builder.setPositiveButton("Check Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void setTagName() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void replaceFragment(String fragment_header_name, BaseFragment baseFragment, String fragment_id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, baseFragment, fragment_id);
        fragmentTransaction.addToBackStack(fragment_id);
        fragmentTransaction.commit();
    }
    @Override
    public void setTitle(String name){
        getSupportActionBar().setTitle(name);
    }
}
