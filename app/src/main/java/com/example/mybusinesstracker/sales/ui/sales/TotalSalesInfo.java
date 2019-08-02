package com.example.mybusinesstracker.sales.ui.sales;

import androidx.databinding.BaseObservable;

public class TotalSalesInfo extends BaseObservable {
    int totalDue;
    int totalPaid;
    int totalICEAmount;
    int totalLabourCharges;
    int totalAmount;
    float totalBlock;
    String totalBlocksString;
    String headerText;

    public int getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(int totalDue) {
        this.totalDue = totalDue;
    }

    public int getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(int totalPaid) {
        this.totalPaid = totalPaid;
    }

    public int getTotalICEAmount() {
        return totalICEAmount;
    }

    public void setTotalICEAmount(int totalICEAmount) {
        this.totalICEAmount = totalICEAmount;
    }

    public int getTotalLabourCharges() {
        return totalLabourCharges;
    }

    public void setTotalLabourCharges(int totalLabourCharges) {
        this.totalLabourCharges = totalLabourCharges;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalBlock() {
        return totalBlock;
    }

    public void setTotalBlock(float totalBlock) {
        this.totalBlock = totalBlock;
    }

    public String getTotalBlocksString() {
        return totalBlocksString;
    }

    public void setTotalBlocksString(String totalBlocksString) {
        this.totalBlocksString = totalBlocksString;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }
}
