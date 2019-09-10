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
import com.example.mybusinesstracker.cloud_firestore.tables.CustomerTable;
import com.example.mybusinesstracker.customer.CustomerFragmentInteractionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CustomerListView extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Customer> mParam1;
    protected ArrayList<Customer> mAllCustomers = new ArrayList<>();
    private CustomerFragmentInteractionListener mListener;
    public CustomerAdapter mAdapter;
    public CustomerListView() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CustomerViewHolder.REQUEST_PHONE_CALL:

                break;
        }

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

        new CustomerTable().getCustomerList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(null != task.getResult()) {
                    mAllCustomers.clear();
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        assert data != null;
                        addCustomer(new Customer(data));
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.customer_list);
        view.findViewById(R.id.add_new).setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // specify an adapter (see also next example)
        mAdapter = new CustomerAdapter(mAllCustomers,mListener);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
    protected void addCustomer(Customer customer) {
        mAllCustomers.add(customer);
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
