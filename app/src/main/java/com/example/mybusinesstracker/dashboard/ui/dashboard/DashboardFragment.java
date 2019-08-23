package com.example.mybusinesstracker.dashboard.ui.dashboard;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
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
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cabin.ui.cabinhome.OnCabinInteractionListener;
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

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardViewModel mViewModel = new DashboardViewModel();
    //CabinViewModel mCabinViewModel;
    private CabinBrickAdapter cabinBrickAdapter;
    private RecyclerView recyclerView;
    private OnCabinInteractionListener onCabinInteractionListener;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onCabinInteractionListener = (OnCabinInteractionListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardFragmentBinding dashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
        mViewModel.setCabinViewModel(new CabinViewModel());
        mViewModel.setSelectedDate(Calendar.getInstance());
        View  view = dashboardFragmentBinding.getRoot(); //inflater.inflate(R.layout.dashboard_fragment, container, false);
        recyclerView = view.findViewById(R.id.cabin);
        view.findViewById(R.id.submit).setOnClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        cabinBrickAdapter = new CabinBrickAdapter(mViewModel);
        CabinTable cabinTable = new CabinTable();
        cabinTable.getCabinList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        CabinViewModel cabinViewModel = document.toObject(CabinViewModel.class);
                        mViewModel.getCabinViewModel().cloneCabinViewModel(cabinViewModel, Calendar.getInstance());
                    }
                    if (mViewModel.getCabinViewModel().getCabinSize() > 0) {
                        updateList();
                        getSalesData();
                    }
                }
            }
        });

        dashboardFragmentBinding.setCreteViewModel(mViewModel);
        dashboardFragmentBinding.setIceCabinAdapter(cabinBrickAdapter);
        return view;
    }

    private void getSalesData() {
        SalesTable salesTable = new SalesTable();
        salesTable.getDaySales(mViewModel.getSelectedDate(), mViewModel.getCabinViewModel().getCabinName(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (null != task.getResult()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        assert data != null;
                        SalesViewModel salesViewModel = new SalesViewModel(data);
                        mViewModel.setAddNewSales(salesViewModel);
                    }
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void updateList() {
        ((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).setSpanCount(mViewModel.getCabinViewModel().getTotalColumns());
        cabinBrickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        mViewModel.getAddNewSales().setCabinID(mViewModel.getCabinViewModel().getCabinName());
        mViewModel.getAddNewSales().setDate(mViewModel.getSelectedDate().getTimeInMillis(), Utils.DD_MMM_YYYY);
        onCabinInteractionListener.gotoSalesActivity(mViewModel.getAddNewSales());
    }
}
