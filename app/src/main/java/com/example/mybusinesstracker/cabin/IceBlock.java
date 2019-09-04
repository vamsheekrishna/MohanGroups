package com.example.mybusinesstracker.cabin;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.R;

import java.io.Serializable;
import java.util.Map;

public class IceBlock extends BaseObservable implements Serializable {

    private int ID;
    private boolean inProduction = true;
    private boolean iceBlock = true;
    private long startedAt;
    private String blockName;
    private boolean isClickable = true;
    private int block1, block2, block3, block4, blockBG, iceColor, selectedColor = R.color.light_gray;
    private boolean isSelected;

    public IceBlock() {

    }
    public IceBlock(int i, String name) {
        setID(i);
        setBlockName(name);
        setFullBlockColor(-1);
    }
    public IceBlock(Map<String, Object> data) {
        ID = (int) data.get("ID");
        inProduction = (boolean) data.get("inProduction");
        iceBlock = (boolean) data.get("iceBlock");
        startedAt = (long) data.get("startedAt");
        blockName = (String) data.get("blockName");
    }
    public boolean isIceBlock() {
        return iceBlock;
    }

    public void setIceBlock(boolean isIceBlock) {
        iceBlock = isIceBlock;
        /*
            if(iceBlock) {
                iceBlock = false;
                //setBlockBG(getSelectedColor());
            } else  {
                iceBlock = true;
                setBlockBG(getIceColor());
            }
        */
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
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
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
        if(isSelected) {
            return getSelectedColor();
        } else {
            return getIceColor();
        }
    }

    public void setBlockBG(int blockBG) {
        this.blockBG = blockBG;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    private int getIceColor() {
        return iceColor;
    }

    public void setIceColor(int iceColor) {
        this.iceColor = iceColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    private int getSelectedColor() {
        return selectedColor;
    }


    public void setBlockSelectedState() {
        isSelected = !isSelected;
    }
    public void setBlockSelectedState(boolean state) {
        isSelected = state;
    }
    public boolean getBlockSelectedState() {
        return isSelected;
    }
}
