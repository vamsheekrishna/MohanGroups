package com.example.mybusinesstracker.sales.ui.sales;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.basecalss.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.ui.cabinhome.IceBlock;
import com.example.mybusinesstracker.cabin.ui.cabinhome.IceBlockPOJO;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cabin.ui.cabinhome.OnCabinInteractionListener;
import com.example.mybusinesstracker.cloud_firestore.tables.CabinTable;
import com.example.mybusinesstracker.cloud_firestore.tables.CustomerTable;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.customer.CustomerActivity;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardViewModel;
import com.example.mybusinesstracker.databinding.SalesFragmentBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddSaleFragment extends BaseFragment implements View.OnClickListener, OnFailureListener, OnSuccessListener<Void> {

    private static final String ARG_SALES_MODEL = "SalesViewModel";
    private static final String ARG_CABIN_MODEL = "cabinModule";
    public static final String IS_NEW = "IS_NEW";
    //private SalesViewModel mViewModel;
    private ArrayList<String> mSalesTypes = new ArrayList<>();
    private ArrayList<String> mCabinNames = new ArrayList<>();
    private ArrayList<String> mCustomerNames = new ArrayList<>();
    //AdapterView.OnItemSelectedListener spinnerSaleTypeAdapter, spinnerCabinAdapter, spinnerCustomerAdapter;
    private ArrayAdapter<String> salesTypeAdapter;
    private ArrayAdapter<String> spinnerCustomerAdapter;
    private ArrayAdapter<String> spinnerCabinAdapter;
    private OnSalesInteractionListener mListener;
    private OnCabinInteractionListener onCabinInteractionListener;
    private int testValue = 0;
    private SalesViewModel mSalesViewModel;
    private CabinViewModel mCabinViewModel;
    private CheckBox isInProduction;
    private HashMap<String, Customer> mAllCustomers = new HashMap<>();
    private boolean mIsNew = false;
    private Spinner mSpinnerCabin;
    public static AddSaleFragment newInstance(SalesViewModel salesViewModel, boolean isNew) {
        AddSaleFragment fragment = new AddSaleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SALES_MODEL, salesViewModel);
        args.putBoolean(IS_NEW, isNew);
        fragment.setArguments(args);
        return fragment;
    }
    public static AddSaleFragment newInstance(DashboardViewModel dashboardViewModel, boolean isNew) {
        AddSaleFragment fragment = new AddSaleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SALES_MODEL, dashboardViewModel.getAddNewSales());
        args.putSerializable(ARG_CABIN_MODEL, dashboardViewModel.getCabinViewModel());
        args.putBoolean(IS_NEW, isNew);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSalesViewModel = (SalesViewModel) getArguments().getSerializable(ARG_SALES_MODEL);
            mCabinViewModel = (CabinViewModel) getArguments().getSerializable(ARG_CABIN_MODEL);
            mIsNew = getArguments().getBoolean(IS_NEW);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getCustomerList();
        mSalesTypes.add("Sale");
        mSalesTypes.add("Due");
        mSalesTypes.add("Due Paid");
        getCabinList();
        SalesFragmentBinding binder = DataBindingUtil.inflate(inflater, R.layout.sales_fragment, container, false);
        View view = binder.getRoot();
        Spinner mSpinnerEntryType = view.findViewById(R.id.entry_type_sp);
        mSpinnerCabin = view.findViewById(R.id.cab_name_sp);
        Spinner mSpinnerCustomerName = view.findViewById(R.id.sal_cus_ed);
        TextView mDateTextView = view.findViewById(R.id.sal_date_ed);

        mDateTextView.setOnClickListener(this);
        view.findViewById(R.id.sub_btn).setOnClickListener(this);
        view.findViewById(R.id.del_btn).setOnClickListener(this);
        isInProduction = view.findViewById(R.id.restart_checkbox);
        mSpinnerCustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSalesViewModel.setCustomerID(mCustomerNames.get(position));
                mSalesViewModel.setSelectedCustomer(mAllCustomers.get(mSalesViewModel.getCustomerID()));
                mSalesViewModel.setTotalBlocks(mSalesViewModel.getBlocks().size());
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
        updateCustomerSpinner();

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
            mSpinnerCustomerName.setSelection(mCustomerNames.indexOf(mSalesViewModel.getCustomerID()));
            mSpinnerEntryType.setSelection(mSalesTypes.indexOf(mSalesViewModel.getSalesType()));

            if(!mIsNew) {
                ((Button) view.findViewById(R.id.sub_btn)).setText(R.string.cus_update_btn);
                ((Button) view.findViewById(R.id.del_btn)).setText(R.string.cus_delete_btn);
                view.findViewById(R.id.del_btn).setVisibility(View.VISIBLE);
            } else {
                ((Button)view.findViewById(R.id.sub_btn)).setText(R.string.cus_create_btn);
                view.findViewById(R.id.del_btn).setVisibility(View.GONE);
            }
        }

        return view;
    }

    private void getCabinList() {
        CabinTable cabinTable = new CabinTable();
        cabinTable.getCabinList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mCabinNames.clear();
                if(null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        CabinViewModel cabinViewModel = document.toObject(CabinViewModel.class);
                        assert cabinViewModel != null;
                        mCabinNames.add(cabinViewModel.getCabinName());
                    }
                    String name = mSalesViewModel.getCabinID();
                    spinnerCabinAdapter.notifyDataSetChanged();
                    if(name.length()>0) {
                        mSpinnerCabin.setSelection(mCabinNames.indexOf(mSalesViewModel.getCabinID()));
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSalesInteractionListener) {
            mListener = (OnSalesInteractionListener) context;
        } else if (context instanceof OnCabinInteractionListener) {
            onCabinInteractionListener = (OnCabinInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSalesInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getContext() instanceof OnSalesInteractionListener) {
            mListener = null;
        } else if (getContext() instanceof OnCabinInteractionListener) {
            onCabinInteractionListener = null;
        } else {
            throw new RuntimeException(Objects.requireNonNull(getContext()).toString() + " must implement OnSalesInteractionListener");
        }
    }

    public void updateCustomerSpinner() {

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
                if(mSalesViewModel.isInProductionMode()) {
                    if (((TextView) v).getText().toString().equals(getString(R.string.cus_update_btn)))
                        onUpdateClicked();
                    else
                        onSaveClicked();
                }
                updateCabinData();
                break;
            case R.id.del_btn:
                onDeleteClicked();
                break;
        }
    }

    private void updateCabinData() {
        //SalesViewModel salesViewModel = mViewModel.getAddNewSales();
        Calendar calendar = Calendar.getInstance();
        for (IceBlock iceBlock :  mSalesViewModel.getBlocks()) {
            mCabinViewModel.getIceBlockPOJOS().get(iceBlock.getID()).setInProduction(iceBlock.isInProduction());
            mCabinViewModel.getIceBlockPOJOS().get(iceBlock.getID()).setStartedAt(calendar.getTimeInMillis());
            mCabinViewModel.getIceBlockPOJOS().get(iceBlock.getID()).setFullBlockColor(-1);
        }
        CabinTable cabinTable = new CabinTable();
        cabinTable.updateFields(mCabinViewModel.getCabinName(),mCabinViewModel.getIceBlock(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getContext(),"onFailure",Toast.LENGTH_SHORT).show();
            }
        }, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                //Toast.makeText(getContext(),"onSuccess",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onDeleteClicked() {
        SalesTable salesTable = new SalesTable();
        salesTable.deleteRecord(mSalesViewModel, this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (getContext() instanceof OnSalesInteractionListener) {
                    mListener.onDeleteSaleRecordSuccess(mSalesViewModel);
                } else if (getContext() instanceof OnCabinInteractionListener) {
                    onCabinInteractionListener.onAddSaleRecordSuccess(mSalesViewModel);
                } else {
                    throw new RuntimeException(Objects.requireNonNull(getContext()).toString() + " must implement OnSalesInteractionListener");
                }
            }
        });
    }

    private void onSaveClicked() {
        if(null == mSalesViewModel.getDate()) {
            mSalesViewModel.setDate(Calendar.getInstance().getTimeInMillis(), "DD-MM-YYYY HH:mm");
        }
        //Customer customer =mAllCustomers.get(mSalesViewModel.getCustomerID());
        for (IceBlock iceBlock : mSalesViewModel.getBlocks()) {
            //assert customer != null;
            //iceBlock.setFullBlockColor(mSalesViewModel.getSelectedCustomer().getColorID());
            mSalesViewModel.setCustomerColor(mSalesViewModel.getSelectedCustomer().getColorID());
            iceBlock.setInProduction(isInProduction.isChecked());
        }
        SalesTable salesTable = new SalesTable();
        salesTable.addDataField(mSalesViewModel,this, this);
    }

    private void onUpdateClicked() {
        SalesTable salesTable = new SalesTable();
        salesTable.updateFields(mSalesViewModel, this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (getContext() instanceof OnSalesInteractionListener) {
                    mListener.onUpdateSaleRecordSuccess(mSalesViewModel);
                } else if (getContext() instanceof OnCabinInteractionListener) {
                    onCabinInteractionListener.onAddSaleRecordSuccess(mSalesViewModel);
                } else {
                    throw new RuntimeException(Objects.requireNonNull(getContext()).toString() + " must implement OnSalesInteractionListener");
                }
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


        if (getContext() instanceof OnSalesInteractionListener) {
            mListener.onAddSaleRecordSuccess(mSalesViewModel);
        } else if (getContext() instanceof OnCabinInteractionListener) {
            onCabinInteractionListener.onAddSaleRecordSuccess(mSalesViewModel);
        } else {
            throw new RuntimeException(Objects.requireNonNull(getContext()).toString() + " must implement OnSalesInteractionListener");
        }
    }

    private void getCustomerList() {

        CustomerTable customerTable = new CustomerTable();
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

                    if(mAllCustomers.size()>0) {
                        updateCustomerSpinner();
                    } else {
                        Intent intent = new Intent(getActivity(), CustomerActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }
    }

    private void addCustomer(Customer customer) {
        mAllCustomers.put(customer.getCustomerName(), customer);
    }
}


