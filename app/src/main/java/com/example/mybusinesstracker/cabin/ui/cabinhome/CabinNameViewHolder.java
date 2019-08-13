package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;

class CabinNameViewHolder extends RecyclerView.ViewHolder {
    TextView mName;
    public CabinNameViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.cabin_id);
    }
}
