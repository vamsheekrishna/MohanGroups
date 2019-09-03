package com.example.mybusinesstracker.services;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.mybusinesstracker.R;

public class ServiceHomeFragment extends Fragment {

    private ServiceHomeViewModel mViewModel = new ServiceHomeViewModel();

    public static ServiceHomeFragment newInstance() {
        return new ServiceHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_home_fragment, container, false);
        ExpandableListView expandableListView = view.findViewById(R.id.services_list);
        expandableListView.setAdapter(new ServicesListAdapter(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // mViewModel = ViewModelProviders.of(this).get(ServiceHomeViewModel.class);
        // TODO: Use the ServiceModel
    }

}
