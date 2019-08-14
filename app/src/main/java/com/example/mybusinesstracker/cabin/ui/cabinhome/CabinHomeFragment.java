package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mybusinesstracker.basecalss.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cloud_firestore.tables.CabinTable;
import com.example.mybusinesstracker.databinding.CabinHomeFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CabinHomeFragment extends BaseFragment implements View.OnClickListener {

    private CabinHomeViewModel mViewModel;
    private OnCabinInteractionListener onCabinInteractionListener;
    CabinListAdapter cabinListAdapter;
    public static CabinHomeFragment newInstance() {
        return new CabinHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new CabinHomeViewModel();
        CabinHomeFragmentBinding cabinHomeFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.cabin_home_fragment,container,false);
        View view = cabinHomeFragmentBinding.getRoot();//inflater.inflate(R.layout.cabin_home_fragment, container, false);
        CabinTable cabinTable = new CabinTable();
        cabinListAdapter = new CabinListAdapter(mViewModel.arrayList, this);
        cabinTable.getCabinList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        CabinViewModel dungeon= document.toObject(CabinViewModel.class);
                        mViewModel.addCabinViewModel(dungeon);
                    }
                }
                updateList();
                //cabinListAdapter.notifyDataSetChanged();
            }
        });
        RecyclerView cabinListView = view.findViewById(R.id.cabin_list);
        cabinListView.setAdapter(cabinListAdapter);
        view.findViewById(R.id.create_cabin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCabinInteractionListener.goToCreteCabin(null);
            }
        });

        return view;
    }

    private void updateList() {
        cabinListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onCabinInteractionListener = (OnCabinInteractionListener) context;
    }

    @Override
    public void onClick(View view) {
        String temp = ((TextView) view).getText().toString();
        CabinViewModel cabinViewModel=  mViewModel.getCabinViewModel(temp);
        onCabinInteractionListener.goToCreteCabin(cabinViewModel);
    }
}
