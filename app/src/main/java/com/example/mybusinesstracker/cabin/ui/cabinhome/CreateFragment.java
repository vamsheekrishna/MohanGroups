package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybusinesstracker.basecalss.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.IceBlock;
import com.example.mybusinesstracker.cloud_firestore.tables.CabinTable;
import com.example.mybusinesstracker.databinding.CreteFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.Objects;

public class CreateFragment extends BaseFragment implements View.OnClickListener {

    private CabinViewModel mViewModel;
    private CabinBrickAdapter cabinBrickAdapter;
    private GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    private boolean isCabinUpdate = false;
    String cabinName = "";

    public static CreateFragment newInstance(CabinViewModel cabinViewModel) {
        CreateFragment creteFragment = new CreateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", cabinViewModel);
        creteFragment.setArguments(bundle);
        return creteFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreteFragmentBinding creteFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.crete_fragment, container, false);
        if(null == mViewModel) {
            mViewModel = new CabinViewModel();
        } else {
            isCabinUpdate = true;
        }
        creteFragmentBinding.setCreteViewModel(mViewModel);
        View view = creteFragmentBinding.getRoot();
        view.findViewById(R.id.submit).setOnClickListener(this);
        view.findViewById(R.id.insert_cabin).setOnClickListener(this);
        //View view = inflater.inflate(R.layout., container, false);
        //generateCabin();


        recyclerView = view.findViewById(R.id.body);
        cabinBrickAdapter = new CabinBrickAdapter(mViewModel.getIceBlocks(), getContext());
        creteFragmentBinding.setIceBlockAdapter(cabinBrickAdapter);
        if(isCabinUpdate) {
            cabinName = mViewModel.getCabinName();
            gridLayoutManager = new GridLayoutManager(getContext(),mViewModel.getTotalColumns(), RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(gridLayoutManager);
            view.findViewById(R.id.delete_cabin).setVisibility(View.VISIBLE);
            view.findViewById(R.id.delete_cabin).setOnClickListener(this);
            ((Button)view.findViewById(R.id.insert_cabin)).setText(getString(R.string.cus_update_btn));
        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateCabin() {
        if(mViewModel.getCabinSize()>0) {
            mViewModel.getIceBlocks().clear();

            for (int i = 0; i<mViewModel.getCabinSize(); i++) {

                IceBlock iceBlock = new IceBlock(i, String.valueOf((i%mViewModel.getTotalColumns())+1));
                iceBlock.setSelectedColor(R.color.light_gray);
                iceBlock.setIceColor(R.color.blue);
                //iceBlock.setFullBlockColor(Objects.requireNonNull(getActivity()).getColor(R.color.blue));
                mViewModel.addBlock(iceBlock);
                iceBlock.setStartedAt(Calendar.getInstance().getTimeInMillis());
                //iceBlocks.add();
            }
            gridLayoutManager = new GridLayoutManager(getContext(),mViewModel.getTotalColumns(), RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(gridLayoutManager);
            cabinBrickAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Please enter the Rows and Column", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != getArguments()) {
            mViewModel = (CabinViewModel) getArguments().getSerializable("model");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mViewModel = ViewModelProviders.of(this).get(CabinViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:

                generateCabin();

                break;
            case R.id.insert_cabin:
                //Button button = (Button)view;
                if(null== mViewModel.getCabinName() || mViewModel.getCabinName().length()<=0) {
                    Toast.makeText(getActivity(),"Please enter the cabin name.",Toast.LENGTH_SHORT).show();
                } else if(null == mViewModel.getIceBlocks() || mViewModel.getIceBlocks().size()<=0) {
                    Toast.makeText(getActivity(),"Please enter the ice blocks.",Toast.LENGTH_SHORT).show();
                } else  {
                    if(cabinName.length()>0 && !cabinName.equalsIgnoreCase(mViewModel.getCabinName())) {
                        deleteRecord();
                    }
                    for (IceBlock iceBlock : mViewModel.getIceBlocks()) {
                        iceBlock.setIceBlock();
                    }
                    CabinTable cabinTable = new CabinTable();
                    cabinTable.addDataField(mViewModel, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Objects.requireNonNull(getActivity()).onBackPressed();
                            //Toast.makeText(getActivity(), "create_cabin onSuccess", Toast.LENGTH_SHORT).show();
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"create_cabin onFailure",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            break;
            case R.id.delete_cabin:
                deleteRecord();
                getActivity().onBackPressed();
                break;
        }
    }

    private void deleteRecord() {
        CabinTable cabinTable = new CabinTable();
        cabinTable.deleteRecord(cabinName, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }
}
