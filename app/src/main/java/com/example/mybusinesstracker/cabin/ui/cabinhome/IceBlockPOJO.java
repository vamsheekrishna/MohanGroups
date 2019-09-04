package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.R;

import java.io.Serializable;
import java.util.Map;

public class IceBlockPOJO extends BaseObservable implements Serializable {

    private int block1 = 0;
    private int block2 = 0;
    private int block3 = 0;
    private int block4 = 0;
    private int blockBG = 0;
    private int iceColor = 0;
    private int selectedColor = R.color.light_gray;
    private boolean isSelected = false;
    private IceBlock iceBlock;
    private boolean isClickable = true;
    public IceBlockPOJO() {

    }
    public IceBlockPOJO(int i, String name) {
        iceBlock = new IceBlock();
        iceBlock.
        setID(i);
        setBlockName(name);
        setFullBlockColor(0);

    }

    public IceBlock getIceBlock() {
        return iceBlock;
    }

    public void setIceBlock(IceBlock iceBlock) {
        this.iceBlock = iceBlock;
    }

    public boolean isIceBlock() {
        return iceBlock.isIceBlock();
    }

    public void setIceBlock(boolean isIceBlock) {
        iceBlock.setIceBlock(isIceBlock);
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
        return iceBlock.getID();
    }

    public void setID(int ID) {
        iceBlock.setID(ID);
    }

    public String getBlockName() {
        return iceBlock.getBlockName();
    }

    public void setBlockName(String name) {
        iceBlock.setBlockName(name);
        notifyChange();
    }

    public boolean isInProduction() {
        return iceBlock.isInProduction();
    }

    public void setInProduction(boolean inProduction) {
        iceBlock.setInProduction(inProduction);
    }

    public long getStartedAt() {
        return iceBlock.getStartedAt();
    }

    public void setStartedAt(long startedAt) {
        iceBlock.setStartedAt(startedAt);
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
