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
import android.widget.Button;
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

    private static final String ARG_SALES_MODEL = "SalesViewModel";
    //private SalesViewModel mViewModel;
    private ArrayList<String> mSalesTypes = new ArrayList<>();
    private ArrayList<String> mCabinNames = new ArrayList<>();
    private ArrayList<String> mCustomerNames = new ArrayList<>();
    //AdapterView.OnItemSelectedListener spinnerSaleTypeAdapter, spinnerCabinAdapter, spinnerCustomerAdapter;
    private ArrayAdapter<String> salesTypeAdapter;
    private ArrayAdapter<String> spinnerCustomerAdapter;
    private ArrayAdapter<String> spinnerCabinAdapter;
    private OnSalesInteractionListener mListener;
    private int testValue = 0;
    private SalesViewModel mSalesViewModel;

    public static AddSaleFragment newInstance(SalesViewModel salesViewModel) {
        AddSaleFragment fragment = new AddSaleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SALES_MODEL, salesViewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSalesViewModel = (SalesViewModel) getArguments().getSerializable(ARG_SALES_MODEL);
        }
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

        SalesFragmentBinding binder = DataBindingUtil.inflate(inflater, R.layout.sales_fragment, container, false);
        View view = binder.getRoot();
        Spinner mSpinnerEntryType = view.findViewById(R.id.entry_type_sp);
        Spinner mSpinnerCabin = view.findViewById(R.id.cab_name_sp);
        Spinner mSpinnerCustomerName = view.findViewById(R.id.sal_cus_ed);
        TextView mDateTextView = view.findViewById(R.id.sal_date_ed);

        mDateTextView.setOnClickListener(this);
        view.findViewById(R.id.sub_btn).setOnClickListener(this);
        view.findViewById(R.id.del_btn).setOnClickListener(this);

        mSpinnerCustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSalesViewModel.setCustomerID(mCustomerNames.get(position));
                mSalesViewModel.setSelectedCustomer(mListener.getCustomers().get(mSalesViewModel.getCustomerID()));
                if(mSalesViewModel.getTotalBlocks()>0) {
                    mSalesViewModel.setIceAmount((int) (mSalesViewModel.getTotalBlocks()*mSalesViewModel.getSelectedCustomer().getAmount()));
                    mSalesViewModel.setLabourCharges((int) (mSalesViewModel.getTotalBlocks()*mSalesViewModel.getSelectedCustomer().getLaborCharge()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerCabin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSalesViewModel.setCabinID(mCabinNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerEntryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSalesViewModel.setSalesType(mSalesTypes.get(position));
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

        if(null == mSalesViewModel){
            mSalesViewModel = new SalesViewModel();
            setTimeText(Calendar.getInstance());
            binder.setSalesModel(mSalesViewModel);
            ((Button)view.findViewById(R.id.sub_btn)).setText(R.string.cus_create_btn);
            view.findViewById(R.id.del_btn).setVisibility(View.GONE);
        } else {
            binder.setSalesModel(mSalesViewModel);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mSalesViewModel.getDate());
            setTimeText(calendar);
            mSpinnerCabin.setSelection(mCabinNames.indexOf(mSalesViewModel.getCabinID()));
            mSpinnerCustomerName.setSelection(mCustomerNames.indexOf(mSalesViewModel.getCustomerID()));
            mSpinnerEntryType.setSelection(mSalesTypes.indexOf(mSalesViewModel.getSalesType()));
            ((Button)view.findViewById(R.id.sub_btn)).setText(R.string.cus_update_btn);
            ((Button)view.findViewById(R.id.del_btn)).setText(R.string.cus_delete_btn);
            view.findViewById(R.id.del_btn).setVisibility(View.VISIBLE);
        }

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
                if(((TextView)v).getText().equals(R.string.cus_update_btn))
                    onUpdateClicked();
                else
                    onSaveClicked();
                break;
            case R.id.del_btn:
                onDeleteClicked();
                break;
        }
    }

    private void onDeleteClicked() {
        SalesTable salesTable = new SalesTable();
        salesTable.deleteRecord(mSalesViewModel, this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onDeleteSaleRecordSuccess(mSalesViewModel);
            }
        });
    }

    private void onSaveClicked() {
        if(null == mSalesViewModel.getDate()) {
            mSalesViewModel.setDate(Calendar.getInstance().getTimeInMillis(), "DD-MM-YYYY HH:mm");
        }
        SalesTable salesTable = new SalesTable();
        salesTable.addDataField(mSalesViewModel,this, this);
    }

    private void onUpdateClicked() {
        SalesTable salesTable = new SalesTable();
        salesTable.updateFields(mSalesViewModel, this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onUpdateSaleRecordSuccess(mSalesViewModel);
            }
        });
    }
    private void saveTestData() {
        int previousCount = 0;
        for(long i = 0; i < 1000; i++) {
            mSalesViewModel.setDate(i+previousCount, "DD-MM-YYYY HH:mm");
            SalesTable salesTable = new SalesTable();
            salesTable.addDataField(mSalesViewModel,this, this);
            //Toast.makeText(getActivity(),"Created "+i,Toast.LENGTH_SHORT)

        }
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
        mSalesViewModel.setDate(calender.getTimeInMillis(), "yyyy-MM-dd 'T'HH:mm");
        mSalesViewModel.setDateString(today);
    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }

    @Override
    public void onSuccess(Void aVoid) {
        mListener.onAddSaleRecordSuccess(mSalesViewModel);
    }
}


