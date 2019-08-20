package com.example.mybusinesstracker.cabin.ui.cabinhome;

import androidx.lifecycle.ViewModel;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.cabin.IceBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class CabinHomeViewModel extends Observable {
    private HashMap<String, CabinViewModel> cabinViewModelHashMap = new HashMap<>();
    ArrayList<String> arrayList = new ArrayList<>();

    void addCabinViewModel(CabinViewModel model) {
        cabinViewModelHashMap.put(model.getCabinName(), model);
        for (IceBlock iceBlock: model.getIceBlocks()) {
            iceBlock.setSelectedColor(R.color.light_gray);
            iceBlock.setIceColor(R.color.blue);
            iceBlock.setBlockSelectedState(iceBlock.isIceBlock());
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
