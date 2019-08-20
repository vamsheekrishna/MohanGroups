package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;

import java.util.ArrayList;

public class CabinListAdapter extends RecyclerView.Adapter<CabinNameViewHolder> {
    ArrayList<String> cabinNames;
    View.OnClickListener onClickListener;
    public CabinListAdapter(ArrayList<String> arrayList, View.OnClickListener click) {
        cabinNames = arrayList;
        onClickListener = click;
    }

    @NonNull
    @Override
    public CabinNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_recycle_cabin_name_item,parent, false);
        CabinNameViewHolder cabinNameViewHolder = new CabinNameViewHolder(view);
        cabinNameViewHolder.mName.setOnClickListener(onClickListener);
        return cabinNameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CabinNameViewHolder holder, int position) {
        holder.mName.setText(cabinNames.get(position));
    }

    @Override
    public int getItemCount() {
        return cabinNames.size();
    }
}
