package com.example.mybusinesstracker.sales.ui.sales;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.databinding.FragmentDiscreteBasedSalesBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

public class DiscreteBasedSalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TotalSalesInfo mTotalSalesInfo = new TotalSalesInfo();
    private String mParam2;

    private OnSalesInteractionListener mListener;
    DiscreteBaseSalesAdapter mDiscreteBaseSalesAdapter;

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
        LinearLayout header = view.findViewById(R.id.row_header);
        ((TextView)header.findViewById(R.id.customer_name)).setText("Time");

        mDiscreteBaseSalesAdapter = new DiscreteBaseSalesAdapter(mTotalSalesInfo.getSalesModels(), this);
        binder.setDiscreteSalesAdapter(mDiscreteBaseSalesAdapter);
        view.findViewById(R.id.add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotToAddSaleFragment(null);
            }
        });
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

    @Override
    public void onClick(View v) {
        SalesViewModel salesViewModel = (SalesViewModel) v.getTag();
        Toast.makeText(getActivity(),"SalesViewModel: "+salesViewModel.getCustomerID(), Toast.LENGTH_SHORT).show();
        mListener.gotToAddSaleFragment(salesViewModel);
    }
}
