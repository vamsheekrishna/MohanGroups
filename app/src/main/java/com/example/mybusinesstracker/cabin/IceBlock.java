package com.example.mybusinesstracker.cabin;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class IceBlock extends BaseObservable implements Serializable {

    private int ID;
    private boolean isInProduction;
    private boolean isIceBlock = true;
    private long startedAt;
    private String blockName;
    private int block1, block2, block3, block4, blockBG;

    public IceBlock() {

    }
    public IceBlock(int i, String name) {
        setID(i);
        setBlockName(name);
        setFullBlockColor(R.color.transparent);
        setBlockBG(R.color.blue);
    }
    public IceBlock(Map<String, Object> data) {
        ID = (int) data.get("ID");
        isInProduction = (boolean) data.get("isInProduction");
        isIceBlock = (boolean) data.get("isIceBlock");
        startedAt = (long) data.get("startedAt");
        blockName = (String) data.get("blockName");
    }
    public boolean isIceBlock() {
        return isIceBlock;
    }

    public void setIceBlock() {
        if(isIceBlock) {
            isIceBlock = false;
            setBlockBG(R.color.light_gray);
        } else  {
            isIceBlock = true;
            setBlockBG(R.color.blue);
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String name) {
        this.blockName = name;
        notifyChange();
    }

    public boolean isInProduction() {
        return isInProduction;
    }

    public void setInProduction(boolean inProduction) {
        isInProduction = inProduction;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public int getBlock1() {
        return block1;
    }

    public void setBlockColor1(int block1) {
        this.block1 = block1;
    }

    public int getBlock2() {
        return block2;
    }

    public void setBlockColor2(int block2) {
        this.block2 = block2;
    }

    public int getBlock3() {
        return block3;
    }

    public void setBlockColor3(int block3) {
        this.block3 = block3;
    }

    public int getBlock4() {
        return block4;
    }

    public void setBlockColor4(int block4) {
        this.block4 = block4;
    }
    public void setFullBlockColor(int color) {
        setBlockColor1(color);
        setBlockColor2(color);
        setBlockColor3(color);
        setBlockColor4(color);
    }

    public int getBlockBG() {
        return blockBG;
    }

    public void setBlockBG(int blockBG) {
        this.blockBG = blockBG;
    }
}
