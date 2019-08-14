package com.example.mybusinesstracker.cabin.ui.cabinhome;

import android.util.Log;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.cabin.IceBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CabinViewModel extends BaseObservable implements Serializable {
    private String cabinName;
    private int totalRows;
    private int totalColumns;
    private int totalIceBlocks;
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

    public int getTotalIceBlocks() {
        return totalColumns * totalRows;
    }

    public void setTotalIceBlocks() {
        this.totalIceBlocks = totalIceBlocks;
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
}
