package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.lifecycle.ViewModel;

import com.example.mybusinesstracker.cabin.IceBlock;

import java.util.ArrayList;

public class CabinViewModel extends ViewModel {
    private String cabinName = "asd";
    private int totalRows = 10;
    private int totalColumns = 10;
    private int totalIceBlocks;
    ArrayList<IceBlock> iceBlocks = new ArrayList<>();
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
}
