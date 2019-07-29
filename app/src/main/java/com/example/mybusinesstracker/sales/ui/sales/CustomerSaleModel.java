package com.example.mybusinesstracker.sales.ui.sales;

import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;

import java.util.ArrayList;

public class CustomerSaleModel {
    public Customer customer;
    public ArrayList<SalesViewModel> salesViewModels = new ArrayList<>();
    private CustomerSaleInfo customerSaleInfo;
    CustomerSaleInfo getCustomerSale() {
        if(null == customerSaleInfo && salesViewModels.size()>0) {
            customerSaleInfo = new CustomerSaleInfo();
            customerSaleInfo.name = customer.getCustomerName();
            StringBuilder nameBuilder = new StringBuilder();
            for (SalesViewModel salesViewModel : salesViewModels) {
                nameBuilder.append(salesViewModel.getTotalBlocks()).append(" + ");
                customerSaleInfo.totalBlock = customerSaleInfo.totalBlock+salesViewModel.getTotalBlocks();
                customerSaleInfo.totalAmount = customerSaleInfo.totalAmount+salesViewModel.getTotalAmount();
                customerSaleInfo.totalPaid = customerSaleInfo.totalPaid+salesViewModel.getPaidAmount();
                customerSaleInfo.totalDue = customerSaleInfo.totalDue+salesViewModel.getDueAmount();
                customerSaleInfo.totalICEAmount = customerSaleInfo.totalICEAmount+salesViewModel.getIceAmount();
                customerSaleInfo.totalLabourCharges = customerSaleInfo.totalLabourCharges+ salesViewModel.getLabourCharges();
            }
            customerSaleInfo.totalBlocksString = String.valueOf(nameBuilder.delete(nameBuilder.length() - 3, nameBuilder.length() - 1));
            return customerSaleInfo;
        }
        return customerSaleInfo;
    }
}
