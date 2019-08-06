package com.example.mybusinesstracker.sales.ui.sales;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerSaleModel extends BaseObservable implements Serializable {
    public Customer customer;
    private ArrayList<SalesViewModel> salesViewModels = new ArrayList<>();
    public TotalSalesInfo customerSaleInfo;

    public ArrayList<SalesViewModel> getSalesViewModels() {
        return salesViewModels;
    }

    TotalSalesInfo getCustomerSale() {
        if(null == customerSaleInfo && salesViewModels.size()>0) {
            customerSaleInfo = new TotalSalesInfo();
            if(null != customer.getCustomerName()) {
                customerSaleInfo.setHeaderText(customer.getCustomerName());
            }
            StringBuilder nameBuilder = new StringBuilder();
            for (SalesViewModel salesViewModel : salesViewModels) {
                nameBuilder.append(salesViewModel.getTotalBlocks()).append(" + ");
                customerSaleInfo.setTotalBlock(customerSaleInfo.getTotalBlock()+salesViewModel.getTotalBlocks());
                customerSaleInfo.setTotalAmount( customerSaleInfo.getTotalAmount()+salesViewModel.getTotalAmount());
                customerSaleInfo.setTotalPaid(customerSaleInfo.getTotalPaid()+salesViewModel.getPaidAmount());
                customerSaleInfo.setTotalDue(customerSaleInfo.getTotalDue()+salesViewModel.getDueAmount());
                customerSaleInfo.setTotalICEAmount(customerSaleInfo.getTotalICEAmount()+salesViewModel.getIceAmount());
                customerSaleInfo.setTotalLabourCharges(customerSaleInfo.getTotalLabourCharges()+ salesViewModel.getLabourCharges());
            }
            //customerSaleInfo.setTotalBlocksString( String.valueOf(nameBuilder.delete(nameBuilder.length() - 3, nameBuilder.length() - 1)));
            return customerSaleInfo;
        }
        return customerSaleInfo;
    }
}
