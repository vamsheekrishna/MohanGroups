package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.IceBlock;
import com.example.mybusinesstracker.databinding.CreteFragmentBinding;

import java.util.ArrayList;
import java.util.Objects;

public class CreteFragment extends BaseFragment implements View.OnClickListener {

    private CabinViewModel mViewModel;
    private CabinBrickAdapter cabinBrickAdapter;
    private GridLayoutManager gridLayoutManager;
    public static CreteFragment newInstance() {
        return new CreteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreteFragmentBinding creteFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.crete_fragment, container, false);
        mViewModel = new CabinViewModel();
        creteFragmentBinding.setCreteViewModel(mViewModel);
        View view = creteFragmentBinding.getRoot();
        view.findViewById(R.id.submit).setOnClickListener(this);
        //View view = inflater.inflate(R.layout., container, false);
        //generateCabin();


        RecyclerView recyclerView = view.findViewById(R.id.body);
        gridLayoutManager = new GridLayoutManager(getContext(),9, RecyclerView.VERTICAL,false);
        //AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(gridLayoutManager);

        cabinBrickAdapter = new CabinBrickAdapter(mViewModel.getIceBlocks(),this);
        creteFragmentBinding.setIceBlockAdapter(cabinBrickAdapter);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateCabin() {
        for (int i =0; i<mViewModel.getTotalIceBlocks(); i++) {
            IceBlock iceBlock = new IceBlock(i+1, String.valueOf((i%mViewModel.getTotalColumns())+1));
            //iceBlock.setFullBlockColor(Objects.requireNonNull(getActivity()).getColor(R.color.blue));
            mViewModel.addBlock(iceBlock);
            //iceBlocks.add();
        }
        gridLayoutManager.setSpanCount(mViewModel.getTotalColumns());
        cabinBrickAdapter.setIceBlocks(mViewModel.getIceBlocks());
        cabinBrickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CabinViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Button button = (Button)view;
                if(button.getText().toString().equalsIgnoreCase(getString(R.string.create_cabin))) {
                    cabinBrickAdapter.getIceBlocks();
                    Toast.makeText(getActivity(),"create_cabin Clicked",Toast.LENGTH_SHORT).show();

                } else {
                    generateCabin();
                    button.setText(getString(R.string.create_cabin));
                    Toast.makeText(getActivity(),"show cabin Clicked",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
