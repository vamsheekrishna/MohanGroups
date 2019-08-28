package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.IceBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CabinViewModel extends BaseObservable implements Serializable {

    private Calendar currentDate;
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
    private ArrayList<IceBlock> iceBlocks = new ArrayList<>();

    public CabinViewModel(Map<String, Object> data) {
        cabinName = (String) data.get("cabinName");
        Long temp;
        temp = (Long) data.get("totalRows") ;
        totalRows=temp.intValue();
        temp = (Long) data.get("totalColumns") ;
        totalColumns = temp.intValue();
        ArrayList<IceBlock> iceBlocksTemp = new ArrayList<>();
        iceBlocksTemp.addAll((Collection<? extends IceBlock>) data.get("iceBlocks"));

    }

    public void cloneCabinViewModel(CabinViewModel data, Calendar calendar) {
        currentDate = calendar;
        cabinName = data.getCabinName();
        totalRows=data.getTotalRows();
        totalColumns = data.totalColumns;
        iceBlocks.clear();
        for (IceBlock iceBlock : data.getIceBlocks()) {
            if(iceBlock.isIceBlock()) {
                totalIceBlocks+=1;
                if(iceBlock.isInProduction()) {
                    long seconds = (currentDate.getTimeInMillis() - iceBlock.getStartedAt()) / 1000;
                    int hours = (int) (seconds / 3600);
                    if(hours >= 48) {
                        availableBlocks+=1;
                        iceBlock.setIceColor(R.color.ice_block_full);
                    } else if(hours>=36 && hours < 47) {
                        threeFourth+=1;
                        iceBlock.setIceColor(R.color.ice_block_threeforth);
                    } else if(hours>=24 && hours < 36) {
                        oneTwo+=1;
                        iceBlock.setIceColor(R.color.ice_block_half);
                    } else if(hours>=12 && hours < 24) {
                        oneFourth+=1;
                        iceBlock.setIceColor(R.color.ice_block_one_fourth);
                    } else {
                        emptyBlocks+=1;
                        iceBlock.setIceColor(R.color.ice_block_empty);
                        iceBlock.setClickable(false);
                    }
                } else {
                    outSide+=1;
                    iceBlock.setIceColor(R.color.ice_block_out_side);
                    iceBlock.setClickable(false);
                }
            } else {
                iceBlock.setIceColor(R.color.light_gray);
                iceBlock.setClickable(false);
            }
            iceBlock.setSelectedColor(R.color.ice_block_out_side);
            iceBlocks.add(iceBlock);
            //long hours = ChronoUnit.HOURS.between(currentDate.toInstant(), c2.toInstant());
        }
        //iceBlocks.addAll(data.iceBlocks);
        /*ArrayList<IceBlock> iceBlocksTemp = new ArrayList<>();
        iceBlocksTemp.addAll((Collection<? extends IceBlock>) data.get("iceBlocks"));*/

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

    public void addBlock(IceBlock iceBlock) {
        iceBlocks.add(iceBlock);
    }
    public IceBlock getIceBlock(int position) {
        return iceBlocks.get(position);
    }
    public  ArrayList<IceBlock> getIceBlocks() {
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

        objectHashMap.put("iceBlocks",iceBlocks);
        return objectHashMap;
    }

    public void addTempData(int size) {
        for(int i =0; i<size; i++) {
            addBlock(new IceBlock(i,String.valueOf(i)));
        }
    }
}
