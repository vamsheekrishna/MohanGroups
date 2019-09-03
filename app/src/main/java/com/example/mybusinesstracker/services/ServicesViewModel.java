package com.example.mybusinesstracker.services;

import androidx.databinding.BaseObservable;

import java.util.ArrayList;

public class ServicesViewModel extends BaseObservable {

    String[] serviceListItems = {"Property Tax", "Home"};

    public ArrayList<ServicesListItem> getServicesListItems() {
        return servicesListItems;
    }

    public void setServicesListItems(ArrayList<ServicesListItem> servicesListItems) {
        this.servicesListItems = servicesListItems;
    }

    ArrayList<ServicesListItem> servicesListItems;

    public void setUpData(){
        ServicesListItem it = new ServicesListItem();
        for (String item: serviceListItems) {
            it.setServiceName(item);
            servicesListItems.add(it);
        }
        notifyChange();
    }

}
