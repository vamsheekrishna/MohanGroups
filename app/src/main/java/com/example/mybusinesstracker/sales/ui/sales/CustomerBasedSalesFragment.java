package com.example.mybusinesstracker.sales.ui.sales;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.databinding.FragmentCustomerBasedSalesBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

public class CustomerBasedSalesFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CustomerSaleModel mCustomerSaleModel;
    private String mParam2;

    private OnSalesInteractionListener mListener;

    public CustomerBasedSalesFragment() {
        // Required empty public constructor
    }

    public static CustomerBasedSalesFragment newInstance(CustomerSaleModel param1, String param2) {
        CustomerBasedSalesFragment fragment = new CustomerBasedSalesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustomerSaleModel = (CustomerSaleModel) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCustomerBasedSalesBinding binder = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_based_sales, container, false);
        binder.setCustomerinfo(mCustomerSaleModel);
        View view =  binder.getRoot();//inflater.inflate(R.layout.fragment_customer_based_sales, fragmet, false);
        ((TextView)view.findViewById(R.id.selected_date)).setText(mParam2);
        CustomerBaseSalesAdapter customerBaseSalesAdapter = new CustomerBaseSalesAdapter(mCustomerSaleModel.salesViewModels, this);
        binder.setMyAdapter(customerBaseSalesAdapter);
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
