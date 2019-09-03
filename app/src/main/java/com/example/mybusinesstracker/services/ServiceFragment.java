package com.example.mybusinesstracker.services;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.basecalss.BaseFragment;

public class ServiceFragment extends BaseFragment {

    private ServiceModel mViewModel = new ServiceModel();

    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout._fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(ServiceModel.class);
        // TODO: Use the ServiceModel
    }

}
