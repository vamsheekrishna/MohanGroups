package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.IceBlock;
import com.example.mybusinesstracker.databinding.FragmentRecycleCabinBrickItemBinding;

import java.util.Objects;

class CabinBrickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView blockPart1, blockPart2, blockPart3, blockPart4, blockBG;
    private FragmentRecycleCabinBrickItemBinding itemBinding;
    View view;
    private Context context;
    private CabinBrickAdapter mCabinBrickAdapter;
    public CabinBrickViewHolder(@NonNull FragmentRecycleCabinBrickItemBinding itemView, CabinBrickAdapter cabinBrickAdapter) {
        super(itemView.getRoot());
        itemBinding = itemView;
        view = itemView.getRoot();
        view.setOnClickListener(this);
        mCabinBrickAdapter = cabinBrickAdapter;
        context = view.getContext();
        blockPart1 = itemView.getRoot().findViewById(R.id.block_piece_1);
        blockPart2 = itemView.getRoot().findViewById(R.id.block_piece_2);
        blockPart3 = itemView.getRoot().findViewById(R.id.block_piece_3);
        blockPart4 = itemView.getRoot().findViewById(R.id.block_piece_4);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(IceBlock iceBlock) {
        itemBinding.setVariable(BR.cabinRowItemView, iceBlock);
        //itemBinding.getRoot().setTag(iceBlock);
        updateBackGround(iceBlock);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updateBackGround(IceBlock iceBlock) {
        itemBinding.getRoot().setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlock.getBlockBG()));
        blockPart1.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlock.getBlock1()));
        blockPart2.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlock.getBlock2()));
        blockPart3.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlock.getBlock3()));
        blockPart4.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlock.getBlock4()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if(null!= mCabinBrickAdapter.mDashboardViewModel) {
            mCabinBrickAdapter.mDashboardViewModel.getAddNewSales().setAddIceBlocks(mCabinBrickAdapter.getIceBlocks().get(position));
        }
        mCabinBrickAdapter.getIceBlocks().get(position).setBlockSelectedState();
        updateBackGround(mCabinBrickAdapter.getIceBlocks().get(position));
    }
}
