package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class CabinHomeViewModel extends Observable {
    private HashMap<String, CabinViewModel> cabinViewModelHashMap = new HashMap<>();
    public ArrayList<String> arrayList = new ArrayList<>();

    void addCabinViewModel(CabinViewModel model) {
        cabinViewModelHashMap.put(model.getCabinName(), model);
        arrayList.add(model.getCabinName());
    }
    ArrayList<String> getAllCabinNames() {
        return arrayList;
    }

    String getCabinName(int position) {
        return arrayList.get(position);
    }
    CabinViewModel getCabinViewModel(String cabinName) {
        return cabinViewModelHashMap.get(cabinName);
    }
    HashMap<String, CabinViewModel> getCabinViewModelHashMap() {
        return cabinViewModelHashMap;
    }
}
