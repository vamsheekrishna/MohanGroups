package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybusinesstracker.R;

public class CabinHomeFragment extends Fragment {

    private CabinHomeViewModel mViewModel;
    OnCabinInteractionListener onCabinInteractionListener;

    public static CabinHomeFragment newInstance() {
        return new CabinHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cabin_home_fragment, container, false);
        view.findViewById(R.id.create_cabin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCabinInteractionListener.goToCreteCabin();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CabinHomeViewModel.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onCabinInteractionListener = (OnCabinInteractionListener) context;
    }
}
