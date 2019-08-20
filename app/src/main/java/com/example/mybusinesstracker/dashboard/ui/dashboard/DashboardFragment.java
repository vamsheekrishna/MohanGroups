package com.example.mybusinesstracker.dashboard.ui.dashboard;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinBrickAdapter;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinListAdapter;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cloud_firestore.tables.CabinTable;
import com.example.mybusinesstracker.cloud_firestore.tables.SalesTable;
import com.example.mybusinesstracker.databinding.DashboardFragmentBinding;
import com.example.mybusinesstracker.utilities.Utils;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    //private DashboardViewModel mViewModel;
    CabinViewModel mCabinViewModel;
    private CabinBrickAdapter cabinBrickAdapter;
    private RecyclerView recyclerView;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardFragmentBinding dashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
        mCabinViewModel = new CabinViewModel();
        //mViewModel.addTempData(10);
        View  view = dashboardFragmentBinding.getRoot(); //inflater.inflate(R.layout.dashboard_fragment, container, false);
        recyclerView = view.findViewById(R.id.cabin);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        cabinBrickAdapter = new CabinBrickAdapter(mCabinViewModel.getIceBlocks());
        CabinTable cabinTable = new CabinTable();
        cabinTable.getCabinList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        CabinViewModel cabinViewModel = document.toObject(CabinViewModel.class);

                        mCabinViewModel.cloneCabinViewModel(cabinViewModel, Calendar.getInstance());
                        //mViewModel = dungeon;
                    }
                    if (mCabinViewModel.getCabinSize() > 0) {
                        updateList();
                        getSalesData();
                    }
                }
                //cabinListAdapter.notifyDataSetChanged();
            }
        });

        dashboardFragmentBinding.setCreteViewModel(mCabinViewModel);
        dashboardFragmentBinding.setIceCabinAdapter(cabinBrickAdapter);
        return view;
    }

    private void getSalesData() {
        SalesTable salesTable = new SalesTable();
        salesTable.getDaySales(Calendar.getInstance(), mCabinViewModel.getCabinName(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        assert data != null;
                        SalesViewModel salesViewModel = new SalesViewModel(data);
                    }
                    //groupBasedSalesAdapter.setSalesViewModels(mGroupBasedSalesModel.getSalesByName().values());
                    //groupBasedSalesAdapter.notifyDataSetChanged();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void updateList() {
        ((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).setSpanCount(mCabinViewModel.getTotalColumns());
        //cabinBrickAdapter.setIceBlocks(mViewModel.getIceBlocks());
        cabinBrickAdapter.notifyDataSetChanged();
    }


}
