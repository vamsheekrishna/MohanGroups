package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.dashboard.ui.dashboard.DashboardViewModel;
import com.example.mybusinesstracker.databinding.FragmentRecycleCabinBrickItemBinding;

import java.util.ArrayList;

public class CabinBrickAdapter extends RecyclerView.Adapter<CabinBrickViewHolder> {
    private ArrayList<IceBlockPOJO> mIceBlockPOJOS;
    DashboardViewModel mDashboardViewModel;
    boolean isCreteCabin = false;
    private Context mContext;
    public CabinBrickAdapter(ArrayList<IceBlockPOJO> iceBlockPOJOS, Context context) {
        mIceBlockPOJOS = iceBlockPOJOS;
        mContext = context;
        isCreteCabin = true;
    }
    public CabinBrickAdapter(DashboardViewModel dashboardViewModel, Context context) {
        mIceBlockPOJOS = dashboardViewModel.getCabinViewModel().getIceBlockPOJOS();
        mDashboardViewModel = dashboardViewModel;
        mContext = context;
        isCreteCabin = false;
    }
    @NonNull
    @Override
    public CabinBrickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentRecycleCabinBrickItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_recycle_cabin_brick_item, parent, false);
        //view.getRoot().setOnClickListener(mOnClickListener);
        //cabinBrickViewHolder.itemView.setOnClickListener(this);
        return new CabinBrickViewHolder(view, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CabinBrickViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.bind(mIceBlockPOJOS.get(position));
        holder.view.setClickable(mIceBlockPOJOS.get(position).isClickable());
    }
    public void setIceBlocks(ArrayList<IceBlockPOJO> iceBlockPOJOS) {
        mIceBlockPOJOS.clear();
        mIceBlockPOJOS.addAll(iceBlockPOJOS);
        //notifyDataSetChanged();
    }
    ArrayList<IceBlockPOJO> getIceBlocks() {
        return mIceBlockPOJOS;
    }

    @Override
    public int getItemCount() {
        return mIceBlockPOJOS.size();
    }

    void showToast(String s) {
        Toast.makeText(mContext, s,Toast.LENGTH_SHORT).show();
    }

    boolean isCreateCabin() {
        return isCreteCabin;
    }

    /*
        @Override
        public void onClick(View view) {
            //CabinBrickViewHolder cabinBrickViewHolder = (CabinBrickViewHolder)view;
            *//*int position = (int) view.getTag();
            mIceBlockPOJOS.get(position).setIceBlock();*//*
            //notifyDataSetChanged();
        }
    */
}
