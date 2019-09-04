package com.example.mybusinesstracker.cabin.ui.cabinhome;


import com.example.mybusinesstracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class CabinHomeViewModel extends Observable {
    private HashMap<String, CabinViewModel> cabinViewModelHashMap = new HashMap<>();
    ArrayList<String> arrayList = new ArrayList<>();

    void addCabinViewModel(CabinViewModel model) {
        cabinViewModelHashMap.put(model.getCabinName(), model);
        for (IceBlockPOJO iceBlockPOJO : model.getIceBlockPOJOS()) {
            iceBlockPOJO.setSelectedColor(R.color.light_gray);
            iceBlockPOJO.setIceColor(R.color.blue);
            if(!iceBlockPOJO.isIceBlock()) {
                iceBlockPOJO.setBlockSelectedState(true);
            }
        }
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
