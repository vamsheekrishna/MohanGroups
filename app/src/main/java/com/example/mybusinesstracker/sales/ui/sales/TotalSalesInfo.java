package com.example.mybusinesstracker.sales.ui.sales;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class TotalSalesInfo extends BaseObservable implements Serializable {
    private int totalDue;
    private int totalPaid;
    public int totalICEAmount;
    private int totalLabourCharges;
    public int totalAmount;
    private float totalBlock;
    public String totalBlocksString = "";
    public String name = "";
    String headerText = "";
    String headerSubText = "";
    private ArrayList<SalesViewModel> salesModels = new ArrayList<>();

    public int getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(int totalDue) {
        this.totalDue = totalDue;
        notifyChange();
    }

    public int getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(int totalPaid) {
        this.totalPaid = totalPaid;
        notifyChange();
    }

    int getTotalICEAmount() {
        return totalICEAmount;
    }

    void setTotalICEAmount(int totalICEAmount) {
        this.totalICEAmount = totalICEAmount;
        notifyChange();
    }

    public int getTotalLabourCharges() {
        return totalLabourCharges;
    }

    public void setTotalLabourCharges(int totalLabourCharges) {
        this.totalLabourCharges = totalLabourCharges;
        notifyChange();
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        notifyChange();
    }

    public float getTotalBlock() {
        return totalBlock;
    }

    public void setTotalBlock(float totalBlock) {
        this.totalBlock = totalBlock;
        notifyChange();
    }

    String getTotalBlocksString() {
        return totalBlocksString;
    }

    void setTotalBlocksString(float totalBlocksString) {
        if(getTotalBlocksString().length()>0) {
            this.totalBlocksString =this.totalBlocksString +" + "+ totalBlocksString;
        } else {
            this.totalBlocksString = String.valueOf(totalBlocksString);
        }
        notifyChange();
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
        notifyChange();
    }

    void addSale(SalesViewModel temp) {
        salesModels.add(temp);
        //
        setTotalBlock(getTotalBlock()+temp.getTotalBlocks());
        setTotalAmount(getTotalAmount()+temp.getTotalAmount());
        setTotalICEAmount(getTotalICEAmount()+temp.getIceAmount());
        setTotalLabourCharges(getTotalLabourCharges()+temp.getLabourCharges());
        setTotalPaid(getTotalPaid()+temp.getPaidAmount());
        setTotalDue(getTotalDue()+temp.getDueAmount());
        setTotalBlocksString(temp.getTotalBlocks());
    }
    void resetValues() {
        salesModels.clear();
        //
        setTotalBlock(0);
        setTotalAmount(0);
        setTotalICEAmount(0);
        setTotalLabourCharges(0);
        setTotalPaid(0);
        setTotalDue(0);
        setTotalBlocksString(0);
    }
    public ArrayList<SalesViewModel> getSalesModels() {
        return salesModels;
    }

    public String getHeaderSubText() {
        return headerSubText;
    }

    public void setHeaderSubText(String headerSubText) {
        this.headerSubText = headerSubText;
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
