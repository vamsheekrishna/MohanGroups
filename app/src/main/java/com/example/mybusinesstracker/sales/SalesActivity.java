package com.example.mybusinesstracker.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.tables.CustomerTable;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.factory.FactoryBaseActivity;
import com.example.mybusinesstracker.sales.ui.sales.AddSaleFragment;
import com.example.mybusinesstracker.sales.ui.sales.CustomerBasedSalesFragment;
import com.example.mybusinesstracker.sales.ui.sales.CustomerSaleModel;
import com.example.mybusinesstracker.sales.ui.sales.DiscreteBasedSalesFragment;
import com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesFragment;
import com.example.mybusinesstracker.sales.ui.sales.MonthSaleFragment;
import com.example.mybusinesstracker.sales.ui.sales.TotalSalesInfo;
import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SalesActivity extends FactoryBaseActivity implements OnSalesInteractionListener{


    protected HashMap<String, Customer> mAllCustomers = new HashMap<>();
    protected HashMap<Long, SalesViewModel> mAllSales = new HashMap<>();
    private HashMap<String, CustomerSaleModel> saleModelHashMap = new HashMap<>();
    private ArrayList<CustomerSaleModel> listOfCustomerSaleModel = new ArrayList<>();
    private int testValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerTable = new CustomerTable();
        getCustomerList();
        getSalesListFromCloud(Calendar.getInstance());


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, GroupBasedSalesFragment.newInstance()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sales, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sales_month:
                gotToMonthlyFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    AddSaleFragment myFragment = (AddSaleFragment) getSupportFragmentManager().findFragmentByTag("AddSaleFragment");
                    // add your code here
                    if (myFragment != null) {
                        myFragment.updateCustomerSpinner(mAllCustomers);
                    }
                }
            });
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
                    AddSaleFragment myFragment = (AddSaleFragment) getSupportFragmentManager().findFragmentByTag("AddSaleFragment");
                    // add your code here
                    if (myFragment != null) {
                        myFragment.updateCustomerSpinner(mAllCustomers);
                    }
                    /*
                        generateSalesHashMap();
                        GroupBasedSalesFragment fragment = (GroupBasedSalesFragment) getSupportFragmentManager().findFragmentByTag("GroupBasedSalesFragment");
                        // add your code here
                        if (fragment != null) {
                                                                                                        fragment.updateAdapter();
                        }
                    */
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
    public CustomerSaleModel getCustomerSales(String customerID) {

        return saleModelHashMap.get(customerID);
    }

    @Override
    public void goToCustomerBasedSalesFragment(String name, String date) {
        CustomerSaleModel customerSaleModel = saleModelHashMap.get(name);
        getSupportActionBar().setTitle(name+" Sales");
        replaceFragment(CustomerBasedSalesFragment.newInstance(customerSaleModel,date), "customer_based_sales_fragment");
        //Toast.makeText(getActivity(),"TotalSalesInfo: "+temp.name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToDiscreteBasedSalesFragment(TotalSalesInfo totalSalesInfo, String header) {
        getSupportActionBar().setTitle(totalSalesInfo.getName().toUpperCase()+ " "+header.toLowerCase());
        replaceFragment(DiscreteBasedSalesFragment.newInstance(totalSalesInfo), "discrete_based_sales_fragment");

    }

    protected void addCustomer(Customer customer) {
        mAllCustomers.put(customer.getCustomerName(), customer);
    }
    protected void addSale(SalesViewModel sale) {
        mAllSales.put(sale.getDate(), sale);
    }
    @Override
    public HashMap<String, Customer> getCustomers() {
        return mAllCustomers;
    }

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
        /*mAllSales.remove(mSalesViewModel.getDate());
        CustomerSaleModel customerSaleModel = saleModelHashMap.get(mSalesViewModel.getCustomerID());
        customerSaleModel.salesViewModels.remove(mSalesViewModel);*/
        SalesActivity.this.onBackPressed();
    }

    @Override
    public HashMap<Long, SalesViewModel> getDaySales() {
        return mAllSales;
    }

    @Override
    public void gotToAddSaleFragment(SalesViewModel salesViewModel) {
        replaceFragment(AddSaleFragment.newInstance(salesViewModel), "add_sale");
    }
    @Override
    public void gotToMonthlyFragment() {
        getSupportActionBar().setTitle("Month Sales");
        replaceFragment(MonthSaleFragment.newInstance("",""), "month_sale_fragment");
    }

}
