package com.example.mybusinesstracker.sales.ui.sales;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class GroupBasedSalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GroupBasedSalesModel mGroupBasedSalesModel = new GroupBasedSalesModel();
    private String mParam2;

    private OnSalesInteractionListener mListener;
    private GroupSalesAdapter groupBasedSalesAdapter;
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
        getSalesGroupArray();
        binder.setGroupTotalSalesModel(mGroupBasedSalesModel.totalSalesInfo);
        View view =  binder.getRoot();//inflater.inflate(R.layout.fragment_customer_based_sales, fragmet, false);
        ((TextView)view.findViewById(R.id.selected_date)).setText(mParam2);
        groupBasedSalesAdapter = new GroupSalesAdapter(mGroupBasedSalesModel.getNameSales(), this);
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
        mGroupBasedSalesModel.setCalendar(Calendar.getInstance().getTimeInMillis());
        mGroupBasedSalesModel.totalSalesInfo.setHeaderText("Frick");
        mGroupBasedSalesModel.totalSalesInfo.setHeaderSubText(Utils.getStringFromDate(mGroupBasedSalesModel.getCalendar(),"dd MMM yyyy"));
        SalesTable salesTable = new SalesTable();
        if(null !=mGroupBasedSalesModel.totalSalesInfo.getHeaderText()&&mGroupBasedSalesModel.totalSalesInfo.getHeaderText().length()>0) {
            salesTable.getDaySales(mGroupBasedSalesModel.getCalendar(), mGroupBasedSalesModel.totalSalesInfo.getHeaderText(), new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (null != task.getResult()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            assert data != null;
                            SalesViewModel salesViewModel = new SalesViewModel(data);
                            mGroupBasedSalesModel.addSale(salesViewModel, mGroupBasedSalesModel.totalSalesInfo.getHeaderText(), mGroupBasedSalesModel.totalSalesInfo.getHeaderSubText());
                            mGroupBasedSalesModel.totalSalesInfo.setName(salesViewModel.getCustomerID());
                        }
                        groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getNameSales().values());
                        groupBasedSalesAdapter.notifyDataSetChanged();
                    }

                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            salesTable.getDaySales(mGroupBasedSalesModel.getCalendar(), new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    DocumentSnapshot document = task.getResult();
                    SnapshotMetadata getMetadata = document.getMetadata();
                    Date getDate = document.getDate("Frick");
                    assert document != null;
                    DocumentReference temp = document.getReference();
                    Map<String, Object> temp1 = document.getData();
                    if (document.exists()) {
                        Log.d("result", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("result", "No such document");
                    }
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
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
        TotalSalesInfo dataModel = (TotalSalesInfo) v.getTag();
        mListener.goToDiscreteBasedSalesFragment(dataModel, "Day Sales");
    }
}
