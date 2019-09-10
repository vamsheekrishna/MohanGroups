package com.example.mybusinesstracker.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ServicePOJOList {


    public HashMap<String, ArrayList<ServiceObject>> getServicesList() {
        return servicesList;
    }

    public void setServicesList(HashMap<String, ArrayList<ServiceObject>> servicesList) {
        this.servicesList = servicesList;
    }

    public HashMap<String, ArrayList<ServiceObject>> servicesList;



    public int getServicesCount(){
        return  servicesList.size();
    }

    public void addService(ServiceObject serviceObject){
//        servicesList.put(serviceObject.getType_key(),serviceObject);
    }
}
