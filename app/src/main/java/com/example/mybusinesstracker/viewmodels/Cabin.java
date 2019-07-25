package com.example.mybusinesstracker.viewmodels;

import java.util.ArrayList;

public class Cabin {
    String cabinName = "";
    int totalBlocks = 0;
    ArrayList<InActiveBlocks> inActiveBlocks = new ArrayList<>();
    int rows = 0;
    int columns = 0;
    //var blockArray:HashMap<String, CabinBlock>? = HashMap()
    private class InActiveBlocks {
        int rowId;
        int columnId;
    }
}
