package com.example.mybusinesstracker.customer.ui.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.basecalss.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.customer.CustomerFragmentInteractionListener;

import java.util.ArrayList;

public class CustomerListView extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Customer> mParam1;

    private CustomerFragmentInteractionListener mListener;
    public CustomerAdapter mAdapter;
    public CustomerListView() {
        // Required empty public constructor
    }

    public static CustomerListView newInstance(ArrayList<Customer> customers) {
        CustomerListView fragment = new CustomerListView();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, customers);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_list_view, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.customer_list);
        view.findViewById(R.id.add_new).setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // specify an adapter (see also next example)
        mAdapter = new CustomerAdapter(mListener.getAllCustomers(),mListener);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomerFragmentInteractionListener) {
            mListener = (CustomerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CustomerFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_new) {
            mListener.goToCreateCustomer();
        }
    }
}
