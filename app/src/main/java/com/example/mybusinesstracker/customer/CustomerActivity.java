package com.example.mybusinesstracker.customer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.DBInstance;
import com.example.mybusinesstracker.cloud_firestore.tables.CustomerTable;
import com.example.mybusinesstracker.customer.ui.customer.CreateCustomer;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.customer.ui.customer.CustomerListView;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CustomerActivity extends FactoryBaseActivity implements CustomerFragmentInteractionListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        customerTable = new CustomerTable();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.coustomer_activity);
        getCustomerList();
        if (savedInstanceState == null) {
            getSupportActionBar().setTitle("Customer List ");
            getSupportFragmentManager().beginTransaction().add(R.id.container, CustomerListView.newInstance(null), "CustomerListView").commitNow();
        }
    }

    @Override
    public void createCustomer(final Customer customer, final boolean isUpdateCustomer) {
        customerTable.addDataField(customer, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DBInstance.BASE_COLLECTION_FACTORY, "Error writing document", e);
            }
        }, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(!isUpdateCustomer) {
                    addCustomer(customer);
                }
                CustomerActivity.this.onBackPressed();
            }
        });
        Toast.makeText(this,"Customer Name: "+customer.getCustomerName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteCustomer(final Customer customer, final Fragment createCustomer) {
        customerTable.deleteRecord(customer, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DBInstance.BASE_COLLECTION_FACTORY, "Error writing document", e);
            }
        }, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAllCustomers.remove(customer);
                Log.d(DBInstance.BASE_COLLECTION_FACTORY, "DocumentSnapshot successfully written!");
                CustomerActivity.this.onBackPressed();
            }
        });
    }
    protected void getCustomerList() {
        if(null == mAllCustomers || mAllCustomers.size()<=0) {
            customerTable.getCustomerList(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(null != task.getResult()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            assert data != null;
                            addCustomer(new Customer(data));
                        }
                    }
                    CustomerListView myFragment = (CustomerListView) getSupportFragmentManager().findFragmentByTag("CustomerListView");
                    // add your code here
                    if (myFragment != null && myFragment.isVisible()) {
                        myFragment.mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    protected void addCustomer(Customer customer) {
        mAllCustomers.add(customer);
    }
    @Override
    public ArrayList<Customer> getAllCustomers() {
        return mAllCustomers;
    }

    @Override
    public void goToCreateCustomer() {
        replaceFragment(CreateCustomer.newInstance(null), "create_customer");
    }



    @Override
    public void goToUpdateCustomer(Customer customer) {
        replaceFragment(CreateCustomer.newInstance(customer), "update_customer");
    }
}
