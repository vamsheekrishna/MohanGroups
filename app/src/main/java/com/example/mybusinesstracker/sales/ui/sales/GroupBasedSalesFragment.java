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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.basecalss.BaseFragment;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesModel.Date.day;
import static com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesModel.Date.month;
import static com.example.mybusinesstracker.sales.ui.sales.GroupBasedSalesModel.Date.year;

public class GroupBasedSalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GroupBasedSalesModel mGroupBasedSalesModel;//= new GroupBasedSalesModel();
    private String mParam2;

    private OnSalesInteractionListener mListener;
    private GroupSalesAdapter groupBasedSalesAdapter;

    //private String dateType = "day";
    //boolean isSingleSaleData = false;
    public GroupBasedSalesFragment() {
        // Required empty public constructor
    }

    public static GroupBasedSalesFragment newInstance(GroupBasedSalesModel totalSalesInfo, Date dateType) {
        GroupBasedSalesFragment fragment = new GroupBasedSalesFragment();
        if(null != totalSalesInfo) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_PARAM1, totalSalesInfo);
            //args.putSerializable(ARG_PARAM2, dateType);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupBasedSalesModel = (GroupBasedSalesModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        if(null == mGroupBasedSalesModel) {
            mGroupBasedSalesModel = new GroupBasedSalesModel();
            setDate(Calendar.getInstance().getTimeInMillis(), Utils.DD_MMM_YYYY);
            mGroupBasedSalesModel.totalSalesInfo.setHeaderText("Frick");
        }
        //String pattern;
        if(mGroupBasedSalesModel.getDateType().equals(day)) {
            //pattern = Utils.DD_MMM_YYYY;
            mListener.setTitle(getString(R.string.daily_sales));
        } else {
            //pattern = Utils.MMM_YYYY;
            mListener.setTitle(getString(R.string.monthly_sales));
        }
        // Inflate the layout for this fragment
        FragmentGroupBasedSalesBinding binder = DataBindingUtil.inflate(inflater, R.layout.fragment_group_based_sales, container, false);
        binder.setGroupTotalSalesModel(mGroupBasedSalesModel.totalSalesInfo);
        View view =  binder.getRoot();
        //((TextView)view.findViewById(R.id.selected_date)).setText(mParam2);
        view.findViewById(R.id.selected_date).setOnClickListener(this);
        groupBasedSalesAdapter = new GroupSalesAdapter(mGroupBasedSalesModel.getSalesData(), this);

        //GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(manager);
        binder.setTotalSales(groupBasedSalesAdapter);
        view.findViewById(R.id.add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotToAddSaleFragment(null);
            }
        });
        return view;
    }

    private void getSalesGroupArray() {
        mGroupBasedSalesModel.clearAllData();
        if(null != groupBasedSalesAdapter) {
            groupBasedSalesAdapter.setSalesViewModels(new ArrayList<TotalSalesInfo>());
            groupBasedSalesAdapter.notifyDataSetChanged();
        }
        SalesTable salesTable = new SalesTable();
        switch (mGroupBasedSalesModel.getDateType()) {
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
                        addSaleToDay(salesViewModel, Utils.DD_MMM_YYYY);
                    }
                    groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesData().values());
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
                        addSaleToDay(salesViewModel, Utils.HH_MM_SS);
                    }
                    //groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByName().values());
                    //groupBasedSalesAdapter.notifyDataSetChanged();
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void addSaleToDay(SalesViewModel salesViewModel, String hhMmSs) {
        mGroupBasedSalesModel.addSale(salesViewModel, mGroupBasedSalesModel.totalSalesInfo.getHeaderText(), mGroupBasedSalesModel.totalSalesInfo.getHeaderSubText(), hhMmSs);
        mGroupBasedSalesModel.totalSalesInfo.setName(salesViewModel.getCustomerID());
    }

    private void setDate(long timeInMillis, String pattern) {
        mGroupBasedSalesModel.clearAllData();
        mGroupBasedSalesModel.setCalendar(timeInMillis);
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
                    TotalSalesInfo totalSalesInfo = (TotalSalesInfo) v.getTag();
                    GroupBasedSalesModel groupBasedSalesModel = new GroupBasedSalesModel();
                    for (SalesViewModel salesViewModel : totalSalesInfo.getSalesModels()) {
                        groupBasedSalesModel.addSale(salesViewModel, groupBasedSalesModel.totalSalesInfo.getHeaderText(), groupBasedSalesModel.totalSalesInfo.getHeaderSubText(), Utils.DD_MMM_YYYY);
                    }

                    if(groupBasedSalesModel.getSalesByDate().size() == groupBasedSalesModel.getSalesByName().size()) {
                        totalSalesInfo.setHeaderSubText(mGroupBasedSalesModel.totalSalesInfo.headerSubText);
                        totalSalesInfo.setHeaderText(mGroupBasedSalesModel.totalSalesInfo.headerText);
                        mListener.goToDiscreteBasedSalesFragment(totalSalesInfo, getString(R.string.daily_sales));
                    } else {

                        if(mGroupBasedSalesModel.getContentType().equals(GroupBasedSalesModel.Content.date)) {
                            groupBasedSalesModel.setContentTypeName(false);
                        } else {
                            groupBasedSalesModel.setContentTypeName(true);
                        }
                        groupBasedSalesModel.setDateType(GroupBasedSalesModel.Date.month);
                        groupBasedSalesModel.totalSalesInfo.setHeaderSubText(totalSalesInfo.headerSubText);
                        groupBasedSalesModel.totalSalesInfo.setHeaderText(totalSalesInfo.getName());

                        /*String format = "";
                        if (mGroupBasedSalesModel.getDateType()  == GroupBasedSalesModel.Date.day) {
                            format = Utils.DD_MMM_YYYY;
                        } else if (mGroupBasedSalesModel.getDateType()  == GroupBasedSalesModel.Date.month) {
                            format = Utils.DD_MMM_YYYY;
                        }*/
                        //groupBasedSalesModel.totalSalesInfo.setName(salesViewModel.getCustomerID());
                        mListener.gotToGroupBasedSalesFragment(groupBasedSalesModel);

                    }
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
                String pattern;
                if(mGroupBasedSalesModel.getDateType().equals(day)) {
                    pattern = Utils.DD_MMM_YYYY;
                } else {
                    pattern = Utils.MMM_YYYY;
                }
                setDate(selectedDate.getTimeInMillis(), pattern);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDailyData() {
        if(!mGroupBasedSalesModel.getDateType().equals(day)) {
            mGroupBasedSalesModel.setDateType(day);
            //nameMenuItem.setVisible(false);
            getSalesGroupArray();
        }
    }

    private void showMonthlyData() {
        if(!mGroupBasedSalesModel.getDateType().equals(month)) {
            mGroupBasedSalesModel.setDateType(month);
            //nameMenuItem.setVisible(true);
            getSalesGroupArray();
        }
    }

    private void showYearlyData() {
        if(!mGroupBasedSalesModel.getDateType().equals(year)) {
            mGroupBasedSalesModel.setDateType(year);
            //nameMenuItem.setVisible(true);
            getSalesGroupArray();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem nameMenuItem = menu.findItem(R.id.button_item);
        RelativeLayout relativeLayout = (RelativeLayout) MenuItemCompat.getActionView(nameMenuItem);
        ToggleButton toggleButton = relativeLayout.findViewById(R.id.button);
        toggleButton.setChecked(mGroupBasedSalesModel.getContentType() == GroupBasedSalesModel.Content.date);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //menu.clear();
         inflater.inflate(R.menu.menu_sales, menu);

        /*if(mGroupBasedSalesModel.getDateType().equals(month)) {
            toggleButton.setChecked(false);
        } else {
            toggleButton.setChecked(true);
        }
        Toast.makeText(getActivity(), "Toggle is: "+toggleButton.isChecked(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Toggle is: "+toggleButton.getText().toString(),Toast.LENGTH_SHORT).show();*/
        //nameMenuItem.setVisible(false);
    }

    public void myFancyMethod(View v) {
        ToggleButton toggleButton = (ToggleButton) v;
        //toggleButton.getSta
        /*if(toggleButton.getText().toString().equalsIgnoreCase("DATE") ) {
        } else {
            groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByName().values());
        }*/
        mGroupBasedSalesModel.setContentTypeName(toggleButton.isChecked());
        groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesData().values());
        groupBasedSalesAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Toggle is: "+toggleButton.isChecked(),Toast.LENGTH_SHORT).show();
    }
}
