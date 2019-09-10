package com.example.mybusinesstracker.services;

import java.net.URL;

public class ServiceObject {



//    private String[] type = {"Property Tax", "Electricity Bill"};
//    private String[] typeDetails = {"Account Name", "Account Number"};

    public String getType_key() {
        return type_key;
    }

    public void setType_key(String type_key) {
        this.type_key = type_key;
    }

    private String type_key;
    private String accountName;
    private String accountNumber;
    private URL url;
//
//    public String[] getTypeDetails() {
//        return typeDetails;
//    }
//
//    public void setTypeDetails(String[] typeDetails) {
//        this.typeDetails = typeDetails;
//    }
//
//    public String[] getType() {
//        return type;
//    }
//
//    public void setType(String[] type) {
//        this.type = type;
//    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
