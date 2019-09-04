package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.databinding.FragmentRecycleCabinBrickItemBinding;

import java.util.Objects;

class CabinBrickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView blockPart1, blockPart2, blockPart3, blockPart4, blockBG;
    private FragmentRecycleCabinBrickItemBinding itemBinding;
    View view;
    private Context context;
    private CabinBrickAdapter mCabinBrickAdapter;
    CabinBrickViewHolder(@NonNull FragmentRecycleCabinBrickItemBinding itemView, CabinBrickAdapter cabinBrickAdapter) {
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
    public void bind(IceBlockPOJO iceBlockPOJO) {
        itemBinding.setVariable(com.example.mybusinesstracker.BR.cabinRowItemView, iceBlockPOJO);
        //itemBinding.getRoot().setTag(iceBlockPOJO);
        updateBackGround(iceBlockPOJO);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateBackGround(IceBlockPOJO iceBlockPOJO) {
        itemBinding.getRoot().setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlockPOJO.getBlockBG()));
        if(iceBlockPOJO.getBlock1() <= 0) {
            blockPart1.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlockPOJO.getBlockBG()));
            blockPart2.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlockPOJO.getBlockBG()));
            blockPart3.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlockPOJO.getBlockBG()));
            blockPart4.setBackgroundColor(Objects.requireNonNull(context).getColor(iceBlockPOJO.getBlockBG()));
        } else {
            blockPart1.setBackgroundColor(iceBlockPOJO.getBlock1());
            blockPart2.setBackgroundColor(iceBlockPOJO.getBlock2());
            blockPart3.setBackgroundColor(iceBlockPOJO.getBlock3());
            blockPart4.setBackgroundColor(iceBlockPOJO.getBlock4());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {

        int position = (int) view.getTag();
        if(mCabinBrickAdapter.isCreateCabin()) {
            if(null!= mCabinBrickAdapter.mDashboardViewModel) {
                mCabinBrickAdapter.mDashboardViewModel.getAddNewSales().setAddIceBlocks(mCabinBrickAdapter.getIceBlocks().get(position));
            }
            mCabinBrickAdapter.getIceBlocks().get(position).setBlockSelectedState();
            updateBackGround(mCabinBrickAdapter.getIceBlocks().get(position));
        } else {
            IceBlockPOJO iceBlockPOJO = mCabinBrickAdapter.getIceBlocks().get(position);
            Boolean isInProductionMode = mCabinBrickAdapter.mDashboardViewModel.getAddNewSales().isInProductionMode();
            if (null != mCabinBrickAdapter.mDashboardViewModel && (null == isInProductionMode || isInProductionMode == iceBlockPOJO.isInProduction())) {
                mCabinBrickAdapter.mDashboardViewModel.getAddNewSales().setAddIceBlocks(iceBlockPOJO);
                iceBlockPOJO.setBlockSelectedState();
                updateBackGround(iceBlockPOJO);
                if (iceBlockPOJO.getBlockSelectedState()) {
                    view.setAlpha(.5f);
                } else {
                    view.setAlpha(1);
                }
            } else {
                mCabinBrickAdapter.showToast("Please select the same type of block");
            }
        }
    }
}
