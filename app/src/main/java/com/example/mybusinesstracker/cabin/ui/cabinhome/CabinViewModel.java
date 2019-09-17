package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CabinViewModel extends BaseObservable implements Serializable {

    private String cabinName;
    private int totalRows;
    private int totalColumns;
    private int totalIceBlocks;
    private int availableBlocks;
    int todaySales;
    private int oneFourth;
    private int threeFourth;
    private int emptyBlocks;
    private int oneTwo;
    private int outSide;
    private ArrayList<IceBlockPOJO> iceBlockPOJOS = new ArrayList<>();

    public void cloneCabinViewModel(CabinViewModel data, Calendar calendar) {
        cabinName = data.getCabinName();
        totalRows=data.getTotalRows();
        totalColumns = data.totalColumns;
        iceBlockPOJOS.clear();
        for (IceBlock iceBlock : data.getIceBlock()) {
            IceBlockPOJO iceBlockPOJO = new IceBlockPOJO();
            iceBlockPOJO.setIceBlock(iceBlock);
            iceBlockPOJO.setSelectedColor(R.color.ice_block_out_side);
            iceBlockPOJO.setBlockSelectedState(false);
            if(iceBlockPOJO.isIceBlock()) {
                totalIceBlocks+=1;
                if(iceBlockPOJO.isInProduction()) {
                    long seconds = (calendar.getTimeInMillis() - iceBlockPOJO.getStartedAt()) / 1000;
                    int hours = (int) (seconds / 3600);
                    if(hours >= 48) {
                        availableBlocks+=1;
                        iceBlockPOJO.setClickable(true);
                        iceBlockPOJO.setIceColor(R.color.ice_block_full);
                        iceBlockPOJO.setBlockBG(R.color.ice_block_full);
                    } else if(hours>=36 && hours < 47) {
                        threeFourth+=1;
                        iceBlockPOJO.setClickable(true);
                        iceBlockPOJO.setIceColor(R.color.ice_block_three_forth);
                        iceBlockPOJO.setBlockBG(R.color.ice_block_three_forth);
                    } else if(hours>=24 && hours < 36) {
                        oneTwo+=1;
                        iceBlockPOJO.setClickable(true);
                        iceBlockPOJO.setIceColor(R.color.ice_block_half);
                        iceBlockPOJO.setBlockBG(R.color.ice_block_half);
                    } else if(hours>=12 && hours < 24) {
                        oneFourth+=1;
                        iceBlockPOJO.setClickable(true);
                        iceBlockPOJO.setIceColor(R.color.ice_block_one_fourth);
                        iceBlockPOJO.setBlockBG(R.color.ice_block_one_fourth);
                    } else {
                        emptyBlocks+=1;
                        iceBlockPOJO.setIceColor(R.color.ice_block_empty);
                        iceBlockPOJO.setBlockBG(R.color.ice_block_empty);
                        iceBlockPOJO.setClickable(true);
                    }
                } else {
                    outSide+=1;
                    iceBlockPOJO.setIceColor(R.color.ice_block_out_side);
                    //iceBlockPOJO.setClickable(false);
                }
            } else {
                iceBlockPOJO.setIceColor(R.color.light_gray);
                iceBlockPOJO.setSelectedColor(R.color.light_gray);
                iceBlockPOJO.setClickable(false);
            }
            iceBlockPOJOS.add(iceBlockPOJO);
        }
    }
    public CabinViewModel() {
    }
    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        //notifyChange();
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public int getCabinSize() {
        return totalColumns * totalRows;
    }

    //public void
    public int getTotalIceBlocks() {
        return totalIceBlocks;
    }

    public void addBlock(IceBlockPOJO iceBlockPOJO) {
        iceBlockPOJOS.add(iceBlockPOJO);
    }
    public IceBlockPOJO getIceBlock(int position) {
        return iceBlockPOJOS.get(position);
    }
    public  ArrayList<IceBlockPOJO> getIceBlockPOJOS() {
        return iceBlockPOJOS;
    }
    public  ArrayList<IceBlock> getIceBlock() {
        ArrayList<IceBlock> iceBlocks = new ArrayList<>();
        for (IceBlockPOJO iceBlockPOJO:iceBlockPOJOS) {
            iceBlocks.add(iceBlockPOJO.getIceBlock());
        }
        return iceBlocks;
    }
    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public HashMap<Object, Object> getHashMap() {
        HashMap<Object, Object>  objectHashMap = new HashMap<>();
        objectHashMap.put("cabinName",cabinName);
        objectHashMap.put("totalRows",totalRows);
        objectHashMap.put("totalColumns",totalColumns);
        /*ArrayList<IceBlock> iceBlocks = new ArrayList<>();
        for (IceBlockPOJO iceBlockPOJO:iceBlockPOJOS) {

            iceBlocks.add(iceBlockPOJO.getIceBlock());
        }*/
        objectHashMap.put("iceBlock", getIceBlock());
        return objectHashMap;
    }

    public void setIceBlock(ArrayList<IceBlock> iceBlock) {
        iceBlockPOJOS = new ArrayList<>();
        for (IceBlock block:iceBlock) {
            IceBlockPOJO iceBlockPOJO = new IceBlockPOJO();
            iceBlockPOJO.setIceBlock(block);
            iceBlockPOJOS.add(iceBlockPOJO);
        }
        //this.iceBlock = iceBlock;
    }

    public void setTotalIceBlocks(int totalIceBlocks) {
        this.totalIceBlocks = totalIceBlocks;
    }

    public int getAvailableBlocks() {
        return availableBlocks;
    }

    public void setAvailableBlocks(int availableBlocks) {
        this.availableBlocks = availableBlocks;
    }

    public int getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(int todaySales) {
        this.todaySales = todaySales;
    }

    public int getOneFourth() {
        return oneFourth;
    }

    public void setOneFourth(int oneFourth) {
        this.oneFourth = oneFourth;
    }

    public int getThreeFourth() {
        return threeFourth;
    }

    public void setThreeFourth(int threeFourth) {
        this.threeFourth = threeFourth;
    }

    public int getEmptyBlocks() {
        return emptyBlocks;
    }

    public void setEmptyBlocks(int emptyBlocks) {
        this.emptyBlocks = emptyBlocks;
    }

    public int getOneTwo() {
        return oneTwo;
    }

    public void setOneTwo(int oneTwo) {
        this.oneTwo = oneTwo;
    }

    public int getOutSide() {
        return outSide;
    }

    public void setOutSide(int outSide) {
        this.outSide = outSide;
    }

    public void addTempData(int size) {
        for(int i =0; i<size; i++) {
            addBlock(new IceBlockPOJO(i,String.valueOf(i)));
        }
    }
}
