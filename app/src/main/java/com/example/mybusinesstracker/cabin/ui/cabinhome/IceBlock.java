package com.example.mybusinesstracker.cabin.ui.cabinhome;

import java.io.Serializable;

public class IceBlock implements Serializable {

    private int ID;
    private boolean inProduction = true;
    private boolean iceBlock = true;
    private long startedAt;
    private String blockName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public boolean isIceBlock() {
        return iceBlock;
    }

    public void setIceBlock(boolean iceBlock) {
        this.iceBlock = iceBlock;
        inProduction = iceBlock;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
