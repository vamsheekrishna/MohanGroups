package com.example.mybusinesstracker.sales.ui.sales;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;

public class DaySalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnSalesInteractionListener mListener;
    private DayRecycleViewAdapter dayRecycleViewAdapter;

    CustomerSaleInfo mTotalSalesInfo = new CustomerSaleInfo();
    private TextView mTVDate, mTVTotalBlock, mTotalAmount, mTotalPaid, mTotalDue;

    final Handler handler = new Handler();
    Timer timer = new Timer();

    public DaySalesFragment() {
        // Required empty public constructor
    }
    public static DaySalesFragment newInstance(String param1) {
        DaySalesFragment fragment = new DaySalesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day_sales, container, false);
        mTVDate = view.findViewById(R.id.selected_date);
        mTVTotalBlock = view.findViewById(R.id.ice_count);
        mTotalAmount = view.findViewById(R.id.total_amount);
        mTotalPaid = view.findViewById(R.id.total_paid);
        mTotalDue = view.findViewById(R.id.total_due);
        view.findViewById(R.id.header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        view.findViewById(R.id.list_header).setBackgroundColor(getActivity().getColor(R.color.highlighted_text_material_light));
        ArrayList<CustomerSaleModel> temp = getCustomerSaleModels();
        mTVDate.setText(mParam1);
        updateHeader();

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotToAddSaleFragment();
            }
        });
        dayRecycleViewAdapter = new DayRecycleViewAdapter(temp, this);
        recyclerView.setAdapter(dayRecycleViewAdapter);

        /*TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            SalesTable salesTable = new SalesTable();
                            salesTable.getSalesList(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    int value = 0;
                                    if(null != task.getResult()) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            Map<String, Object> data = document.getData();
                                            assert data != null;
                                            SalesViewModel temp = new SalesViewModel(data);
                                            value+=1;
                                        }
                                    }
                                    Log.d("saveTestData","Present count: "+value);
                                    Toast.makeText(getActivity(),"Present count: "+value ,Toast.LENGTH_SHORT).show();
                                }
                            }, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Exception: ", "saveTestData onFailure "+e.getMessage());
                                    Toast.makeText(getActivity(),"saveTestData onFailure "+e.getMessage() ,Toast.LENGTH_SHORT).show();

                                }
                            }, String.valueOf(0), String.valueOf(50000));

                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);*/

        return view;
    }

    private void updateHeader() {
        mTVTotalBlock.setText(String.valueOf(mTotalSalesInfo.totalBlock));
        mTotalAmount.setText(String.valueOf(mTotalSalesInfo.totalAmount));
        mTotalPaid.setText(String.valueOf(mTotalSalesInfo.totalPaid));
        mTotalDue.setText(String.valueOf(mTotalSalesInfo.totalDue));
    }

    private ArrayList<CustomerSaleModel> getCustomerSaleModels() {
        ArrayList<CustomerSaleModel> temp = mListener.getSalesList();
        mTotalSalesInfo = new CustomerSaleInfo();
        for (CustomerSaleModel customerSaleModel : temp) {
            for (SalesViewModel salesViewModel : customerSaleModel.salesViewModels) {
                mTotalSalesInfo.totalBlock += salesViewModel.getTotalBlocks();
                mTotalSalesInfo.totalAmount += salesViewModel.getTotalAmount();
                mTotalSalesInfo.totalPaid += salesViewModel.getPaidAmount();
                mTotalSalesInfo.totalDue += salesViewModel.getDueAmount();
            }
        }
        return temp;
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

    public void updateAdapter() {
        getCustomerSaleModels();
        updateHeader();
        dayRecycleViewAdapter.notifyDataSetChanged();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDatePicker(final View v) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mdiDialog =new DatePickerDialog(Objects.requireNonNull(getActivity()),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(),year+ " "+monthOfYear+" "+dayOfMonth,Toast.LENGTH_LONG).show();
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                mTVDate.setText(Utils.getStringFromDate(selectedDate,"dd/mm/yyyy"));
                mListener.getSalesListFromCloud(selectedDate);
                //setTimeText(selectedDate);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mdiDialog.show();
    }

    @Override
    public void onClick(View v) {
        CustomerSaleInfo temp = (CustomerSaleInfo) v.getTag();
        CustomerSaleModel customerSaleModel = mListener.getCustomerSales(temp.name);
        Toast.makeText(getActivity(),"CustomerSaleInfo: "+temp.name, Toast.LENGTH_SHORT).show();
    }
}
