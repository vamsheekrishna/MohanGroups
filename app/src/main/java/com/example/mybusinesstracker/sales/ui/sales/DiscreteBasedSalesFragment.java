package com.example.mybusinesstracker.sales.ui.sales;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.databinding.FragmentDiscreteBasedSalesBinding;
import com.example.mybusinesstracker.databinding.FragmentGroupBasedSalesBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Map;

public class DiscreteBasedSalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TotalSalesInfo mTotalSalesInfo = new TotalSalesInfo();
    private String mParam2;

    private OnSalesInteractionListener mListener;
    CustomerBaseSalesAdapter customerBaseSalesAdapter;
    private GroupSalesAdapter groupBasedSalesAdapter;
    boolean isSingleSaleData = true;
    public DiscreteBasedSalesFragment() {
        // Required empty public constructor
    }

    public static DiscreteBasedSalesFragment newInstance(TotalSalesInfo totalSalesInfo) {
        DiscreteBasedSalesFragment fragment = new DiscreteBasedSalesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, totalSalesInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTotalSalesInfo = (TotalSalesInfo) getArguments().getSerializable(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDiscreteBasedSalesBinding binder = DataBindingUtil.inflate(inflater, R.layout.fragment_discrete_based_sales, container, false);
        //getSalesGroupArray();
        binder.setDiscreteSalesModel(mTotalSalesInfo);
        View view =  binder.getRoot();//inflater.inflate(R.layout.fragment_customer_based_sales, fragmet, false);
        ((TextView)view.findViewById(R.id.selected_date)).setText(mParam2);
        customerBaseSalesAdapter = new CustomerBaseSalesAdapter(mTotalSalesInfo.getSalesModels(), this);
        binder.setDiscreteSalesAdapter(customerBaseSalesAdapter);
        view.findViewById(R.id.add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotToAddSaleFragment(null);
            }
        });
        return view;
    }

    /*private void getSalesGroupArray() {
        mGroupBasedSalesModel.clearAllData();
        mGroupBasedSalesModel.setCalendar(Calendar.getInstance().getTimeInMillis());
        SalesTable salesTable = new SalesTable();
        salesTable.getDaySales(mGroupBasedSalesModel.getCalendar(), "Frick", new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        assert data != null;
                        mGroupBasedSalesModel.addSale(new SalesViewModel(data));
                    }
                    if(isSingleSaleData) {
                        customerBaseSalesAdapter.notifyDataSetChanged();
                    } else {
                        groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getNameSales().values());
                        groupBasedSalesAdapter.notifyDataSetChanged();
                    }
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }*/

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
        SalesViewModel salesViewModel = (SalesViewModel) v.getTag();
        Toast.makeText(getActivity(),"SalesViewModel: "+salesViewModel.getCustomerID(), Toast.LENGTH_SHORT).show();
        mListener.gotToAddSaleFragment(salesViewModel);
    }
}
