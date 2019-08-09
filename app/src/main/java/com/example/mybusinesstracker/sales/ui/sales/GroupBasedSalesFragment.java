package com.example.mybusinesstracker.sales.ui.sales;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.databinding.FragmentGroupBasedSalesBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class GroupBasedSalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GroupBasedSalesModel mGroupBasedSalesModel = new GroupBasedSalesModel();
    private String mParam2;

    private OnSalesInteractionListener mListener;
    private GroupSalesAdapter groupBasedSalesAdapter;
    private Date dateType = Date.day;//0 -> day, 1 -> month, 2 -> year
    private Content content = Content.name;
    //private String dateType = "day";
    //boolean isSingleSaleData = false;
    public GroupBasedSalesFragment() {
        // Required empty public constructor
    }

    public static GroupBasedSalesFragment newInstance() {
        GroupBasedSalesFragment fragment = new GroupBasedSalesFragment();
        /*Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mGroupBasedSalesModel = (CustomerSaleModel) getArguments().getSerializable(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mListener.setTitle("Day Sales");
        FragmentGroupBasedSalesBinding binder = DataBindingUtil.inflate(inflater, R.layout.fragment_group_based_sales, container, false);

        //mGroupBasedSalesModel.clearAllData();
        mGroupBasedSalesModel.totalSalesInfo.setHeaderText("Frick");
        setDate(Calendar.getInstance().getTimeInMillis());

        binder.setGroupTotalSalesModel(mGroupBasedSalesModel.totalSalesInfo);
        View view =  binder.getRoot();//inflater.inflate(R.layout.fragment_customer_based_sales, fragmet, false);
        ((TextView)view.findViewById(R.id.selected_date)).setText(mParam2);
        ((TextView)view.findViewById(R.id.selected_date)).setOnClickListener(this);
        groupBasedSalesAdapter = new GroupSalesAdapter(mGroupBasedSalesModel.getSalesByName(), this);
        binder.setTotalSales(groupBasedSalesAdapter);

        view.findViewById(R.id.add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotToAddSaleFragment(null);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    private void getSalesGroupArray() {
        mGroupBasedSalesModel.clearAllData();
        SalesTable salesTable = new SalesTable();
        switch (dateType) {
            case day:
                dayBased(salesTable);
                break;
            case month:
                monthBased(salesTable);
                break;
            case year:
                yearBased(salesTable);
                break;
        }
    }

    private void yearBased(SalesTable salesTable) {

    }

    private void monthBased(SalesTable salesTable) {
        mGroupBasedSalesModel.totalSalesInfo.setHeaderSubText(Utils.getStringFromDate(mGroupBasedSalesModel.getCalendar(), Utils.MMM_YYYY));
        mListener.setTitle(getString(R.string.monthly_sales));
        salesTable.getMonthSales(mGroupBasedSalesModel.getCalendar(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        assert data != null;
                        SalesViewModel salesViewModel = new SalesViewModel(data);
                        mGroupBasedSalesModel.addSale(salesViewModel, mGroupBasedSalesModel.totalSalesInfo.getHeaderText(), mGroupBasedSalesModel.totalSalesInfo.getHeaderSubText(), Utils.DD_MMM_YYYY);
                        mGroupBasedSalesModel.totalSalesInfo.setName(salesViewModel.getCustomerID());
                    }
                    groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByName().values());
                    groupBasedSalesAdapter.notifyDataSetChanged();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Exception: ","Exception: "+e.getMessage());
            }
        });
    }

    private void dayBased(SalesTable salesTable) {
        mGroupBasedSalesModel.totalSalesInfo.setHeaderSubText(Utils.getStringFromDate(mGroupBasedSalesModel.getCalendar(), Utils.DD_MMM_YYYY));
        mListener.setTitle(getString(R.string.daily_sales));
        salesTable.getDaySales(mGroupBasedSalesModel.getCalendar(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        assert data != null;
                        SalesViewModel salesViewModel = new SalesViewModel(data);
                        mGroupBasedSalesModel.addSale(salesViewModel, mGroupBasedSalesModel.totalSalesInfo.getHeaderText(), mGroupBasedSalesModel.totalSalesInfo.getHeaderSubText(), Utils.HH_MM_SS);
                        mGroupBasedSalesModel.totalSalesInfo.setName(salesViewModel.getCustomerID());
                    }
                    groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByName().values());
                    groupBasedSalesAdapter.notifyDataSetChanged();
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setDate(long timeInMillis) {
        mGroupBasedSalesModel.clearAllData();
        mGroupBasedSalesModel.setCalendar(timeInMillis);
        String pattern;
        if(dateType.equals(Date.day)) {
            pattern = Utils.DD_MMM_YYYY;
        } else {
            pattern = Utils.MMM_YYYY;
        }
        mGroupBasedSalesModel.totalSalesInfo.setHeaderSubText(Utils.getStringFromDate(mGroupBasedSalesModel.getCalendar(), pattern));
        getSalesGroupArray();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selected_date:
                showDatePicker(v);
                break;
                default:
                    TotalSalesInfo dataModel = (TotalSalesInfo) v.getTag();
                    mListener.goToDiscreteBasedSalesFragment(dataModel, "Day Sales");
                    break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDatePicker(final View v) {
        Calendar calendar;
        if(null == mGroupBasedSalesModel.getCalendar()) {
            calendar = Calendar.getInstance();
        } else {
            calendar =mGroupBasedSalesModel.getCalendar();
        }
        DatePickerDialog mdiDialog =new DatePickerDialog(Objects.requireNonNull(getActivity()),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(),year+ " "+monthOfYear+" "+dayOfMonth,Toast.LENGTH_LONG).show();
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                setDate(selectedDate.getTimeInMillis());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        /*if(dateType.equals(Date.month)) {
            mdiDialog.findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
        } else if(dateType.equals(Date.year)) {
            mdiDialog.findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
            mdiDialog.findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
        }*/
        mdiDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sales_day:
                showDailyData();
                return true;
            case R.id.action_sales_month:
                showMonthlyData();
                return true;
            case R.id.action_sales_year:
                showYearlyData();
                return true;
            case R.id.button:
                RelativeLayout view = (RelativeLayout) item.getActionView();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDailyData() {
        if(!dateType.equals(Date.day)) {
            dateType = Date.day;
            getSalesGroupArray();
        }
    }

    private void showMonthlyData() {
        if(!dateType.equals(Date.month)) {
            dateType = Date.month;
            getSalesGroupArray();
        }
    }

    private void showYearlyData() {
        if(!dateType.equals(Date.year)) {
            dateType = Date.day;
            getSalesGroupArray();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //menu.clear();
        inflater.inflate(R.menu.menu_sales,menu);

    }
    enum Date {
        day, month, year;
    }

    enum Content {
        name, date, month;
    }
    public void myFancyMethod(View v) {
        ToggleButton toggleButton = (ToggleButton) v;
        //toggleButton.getSta
        if(toggleButton.getText().toString().equalsIgnoreCase("DATE") ) {
            groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByDate().values());
        } else {
            groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByName().values());
        }
        groupBasedSalesAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Toggle is: "+toggleButton.isChecked(),Toast.LENGTH_SHORT).show();
    }
}
