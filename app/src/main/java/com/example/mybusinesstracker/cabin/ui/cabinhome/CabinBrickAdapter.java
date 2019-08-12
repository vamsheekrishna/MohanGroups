package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.IceBlock;
import com.example.mybusinesstracker.databinding.FragmentRecycleCabinBrickItemBinding;

import java.util.ArrayList;

public class CabinBrickAdapter extends RecyclerView.Adapter<CabinBrickViewHolder> implements View.OnClickListener {
    private ArrayList<IceBlock> mIceBlocks;
    private View.OnClickListener mOnClickListener;
    CabinBrickAdapter(ArrayList<IceBlock> iceBlocks , View.OnClickListener onClickListener) {
        mIceBlocks = iceBlocks;
        mOnClickListener = onClickListener;
    }
    @NonNull
    @Override
    public CabinBrickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentRecycleCabinBrickItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_recycle_cabin_brick_item, parent, false);
        CabinBrickViewHolder cabinBrickViewHolder = new CabinBrickViewHolder(view, this);
        //view.getRoot().setOnClickListener(mOnClickListener);
        //cabinBrickViewHolder.itemView.setOnClickListener(this);
        return cabinBrickViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CabinBrickViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.bind(mIceBlocks.get(position));
    }
    public void setIceBlocks(ArrayList<IceBlock> iceBlocks) {
        mIceBlocks.clear();
        mIceBlocks.addAll(iceBlocks);
    }
    public ArrayList<IceBlock> getIceBlocks() {
        return mIceBlocks;
    }

    @Override
    public int getItemCount() {
        return mIceBlocks.size();
    }

    @Override
    public void onClick(View view) {
        //CabinBrickViewHolder cabinBrickViewHolder = (CabinBrickViewHolder)view;
        /*int position = (int) view.getTag();
        mIceBlocks.get(position).setIceBlock();*/
        //notifyDataSetChanged();
    }
}
