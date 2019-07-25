package com.example.mybusinesstracker.sales.ui.sales;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.databinding.SalesFragmentBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AddSaleFragment extends BaseFragment implements View.OnClickListener, OnFailureListener, OnSuccessListener<Void> {

    private SalesViewModel mViewModel;
    private ArrayList<String> mSalesTypes = new ArrayList<>();
    private ArrayList<String> mCabinNames = new ArrayList<>();
    private ArrayList<String> mCustomerNames = new ArrayList<>();
    //AdapterView.OnItemSelectedListener spinnerSaleTypeAdapter, spinnerCabinAdapter, spinnerCustomerAdapter;
    private ArrayAdapter<String> salesTypeAdapter;
    private ArrayAdapter<String> spinnerCustomerAdapter;
    private ArrayAdapter<String> spinnerCabinAdapter;
    private OnSalesInteractionListener mListener;
    public static AddSaleFragment newInstance() {
        return new AddSaleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mSalesTypes.add("Sale");
        mSalesTypes.add("Due");
        mSalesTypes.add("Due Paid");
        mCabinNames.add("Frick");
        mCabinNames.add("kirloskar");

        mViewModel = new SalesViewModel();
        SalesFragmentBinding binder = DataBindingUtil.inflate(inflater, R.layout.sales_fragment, container, false);
        View view = binder.getRoot();
        binder.setSalesModel(mViewModel);
        Spinner mSpinnerEntryType = view.findViewById(R.id.entry_type_sp);
        Spinner mSpinnerCabin = view.findViewById(R.id.cab_name_sp);
        Spinner mSpinnerCustomerName = view.findViewById(R.id.sal_cus_ed);
        TextView mDateTextView = view.findViewById(R.id.sal_date_ed);
        mDateTextView.setOnClickListener(this);
        view.findViewById(R.id.sub_btn).setOnClickListener(this);
        mSpinnerCustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setCustomerID(mCustomerNames.get(position));
                mViewModel.setSelectedCustomer(mListener.getCustomers().get(mViewModel.getCustomerID()));
                if(mViewModel.getTotalBlocks()>0) {
                    mViewModel.setIceAmount((int) (mViewModel.getTotalBlocks()*mViewModel.getSelectedCustomer().getAmount()));
                    mViewModel.setLabourCharges((int) (mViewModel.getTotalBlocks()*mViewModel.getSelectedCustomer().getLaborCharge()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerCabin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setCabinID(mCabinNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerEntryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setSalesType(mSalesTypes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            spinnerCabinAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, mCabinNames);
            spinnerCustomerAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, mCustomerNames);
            salesTypeAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, mSalesTypes);
        }
        mSpinnerCabin.setAdapter(spinnerCabinAdapter);
        mSpinnerCustomerName.setAdapter(spinnerCustomerAdapter);
        mSpinnerEntryType.setAdapter(salesTypeAdapter);

        updateCustomerSpinner(mListener.getCustomers());
        setTimeText(Calendar.getInstance());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSalesInteractionListener) {
            mListener = (OnSalesInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSalesInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateCustomerSpinner(HashMap<String, Customer> mAllCustomers) {
        mCustomerNames.clear();
        mCustomerNames.addAll(mAllCustomers.keySet());
        spinnerCustomerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sal_date_ed:
                showDatePicker(v);
                break;
            case R.id.sub_btn:
                onSaveClicked();
                break;
        }
    }

    private void onSaveClicked() {
        mViewModel.setDate(Calendar.getInstance().getTimeInMillis());
        SalesTable salesTable = new SalesTable();
        salesTable.addDataField(mViewModel,this, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDatePicker(final View v) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog  mdiDialog =new DatePickerDialog(Objects.requireNonNull(getActivity()),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(),year+ " "+monthOfYear+" "+dayOfMonth,Toast.LENGTH_LONG).show();
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                setTimeText(selectedDate);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mdiDialog.show();
    }

    @SuppressLint("SimpleDateFormat")
    private void setTimeText(Calendar calender) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(calender.getTime());
        System.out.println("Today : " + today);
        mViewModel.setDateString(today);
        mViewModel.setDate(calender.getTimeInMillis());
    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }

    @Override
    public void onSuccess(Void aVoid) {
        mListener.onAddSaleRecordSuccess(mViewModel);
    }
}


