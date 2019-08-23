package com.example.mybusinesstracker.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;
import com.example.mybusinesstracker.sales.ui.sales.AddSaleFragment;
import com.example.mybusinesstracker.sales.ui.sales.DiscreteBasedSalesFragment;
import com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesFragment;
import com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesModel;
import com.example.mybusinesstracker.sales.ui.sales.TotalSalesInfo;
import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SalesActivity extends FactoryBaseActivity implements OnSalesInteractionListener{

    protected HashMap<Long, SalesViewModel> mAllSales = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSalesListFromCloud(Calendar.getInstance());


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, GroupBasedSalesFragment.newInstance(null, null)).commit();
        }
    }

    @Override
    public void getSalesListFromCloud(Calendar calendar) {
        if(null == mAllSales || mAllSales.size() <=0) {
            SalesTable salesTable = new SalesTable();
            salesTable.getSalesList(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(null != task.getResult()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            assert data != null;
                            addSale(new SalesViewModel(data));
                        }
                    }
                    //AddSaleFragment myFragment = (AddSaleFragment) getSupportFragmentManager().findFragmentByTag("AddSaleFragment");
                    // add your code here
                    /*if (myFragment != null) {
                        myFragment.updateCustomerSpinner(mAllCustomers);
                    }*/

                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Exception: ", "Exception "+e.getMessage());
                }
            }, String.valueOf(Utils.getStartOfDay(calendar).getTime()), String.valueOf(Utils.getEndOfDay(calendar).getTime()));
        }
    }


    @Override
    public void goToDiscreteBasedSalesFragment(TotalSalesInfo totalSalesInfo, String header) {
        //getSupportActionBar().setTitle();
        replaceFragment(DiscreteBasedSalesFragment.newInstance(totalSalesInfo, totalSalesInfo.getName().toUpperCase()+ " "+header.toLowerCase()), "discrete_based_sales_fragment");

    }

    protected void addSale(SalesViewModel sale) {
        mAllSales.put(sale.getDate(), sale);
    }
    /*@Override
    public HashMap<String, Customer> getCustomers() {
        return mAllCustomers;
    }*/

    @Override
    public void onAddSaleRecordSuccess(SalesViewModel mViewModel) {
        addSale(mViewModel);
        SalesActivity.this.onBackPressed();
    }

    @Override
    public void onUpdateSaleRecordSuccess(SalesViewModel mViewModel) {
        SalesActivity.this.onBackPressed();
    }

    @Override
    public void onDeleteSaleRecordSuccess(SalesViewModel mSalesViewModel) {
        SalesActivity.this.onBackPressed();
    }

    @Override
    public void gotToAddSaleFragment(SalesViewModel salesViewModel) {
        replaceFragment(AddSaleFragment.newInstance(salesViewModel, false), "add_sale");
    }
    @Override
    public void gotToGroupBasedSalesFragment(GroupBasedSalesModel groupBasedSalesModel) {
        getSupportActionBar().setTitle("Month Sales");
        replaceFragment(GroupBasedSalesFragment.newInstance(groupBasedSalesModel,null), "month_sale_fragment");
    }

    public void myFancyMethod(View view) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
        if(f instanceof GroupBasedSalesFragment)
            // do something with f
            ((GroupBasedSalesFragment) f).myFancyMethod(view);
    }
}
