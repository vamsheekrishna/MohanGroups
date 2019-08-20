package com.example.mybusinesstracker.sales.ui.sales;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.basecalss.BaseFragment;
import com.example.mybusinesstracker.databinding.FragmentDayCabinBinding;
import com.example.mybusinesstracker.sales.OnSalesInteractionListener;


public class DayCabinFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnSalesInteractionListener mListener;

    public DayCabinFragment() {
        // Required empty public constructor
    }

    public static DayCabinFragment newInstance(String param1, String param2) {
        DayCabinFragment fragment = new DayCabinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDayCabinBinding fragmentDayCabinBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_day_cabin,container,false);
        View view = fragmentDayCabinBinding.getRoot();//inflater.inflate(, container, false);
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
}
