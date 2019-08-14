package com.example.mybusinesstracker.factory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.example.mybusinesstracker.basecalss.BaseActivity;
import com.example.mybusinesstracker.cloud_firestore.tables.CustomerTable;
import com.example.mybusinesstracker.customer.ui.customer.Customer;

import java.util.ArrayList;

@SuppressLint("Registered")
public class FactoryBaseActivity extends BaseActivity {
    protected CustomerTable customerTable;
    protected ArrayList<Customer> mAllCustomers = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
