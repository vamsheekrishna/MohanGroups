package com.example.mybusinesstracker.cloud_firestore.tables;

import com.example.mybusinesstracker.cabin.IceBlock;
import com.example.mybusinesstracker.cabin.ui.cabinhome.CabinViewModel;
import com.example.mybusinesstracker.cloud_firestore.DBInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class CabinTable extends DBInstance {
    public CollectionReference getCollection() {
        return DBInstance.getDBInstance().collection(BASE_COLLECTION_FACTORY);
    }
    public void addDataField(CabinViewModel model, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {

        /*getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_CABIN).document(data.getCustomerName()).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
*/
    }

    public void updateFields(String cabinID, ArrayList<IceBlock> selected, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        HashMap<String, Object> date = new HashMap<>();
        //date.put("0", );
        /*IceBlock iceBlock = new IceBlock();
        iceBlock.setFullBlockColor(-1);
        iceBlock.setBlockBG(2131034200);
        iceBlock.setBlockName("1");
        iceBlock.setClickable(true);
        iceBlock.setID(0);
        iceBlock.setInProduction(true);
        iceBlock.setStartedAt(1566882133161l);*/

        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_CABIN).document(cabinID).update("iceBlocks", selected)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }
    public void deleteRecord(String key, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_CABIN).document(key).delete().addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }

    public void getCabinList(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_CABIN).get().addOnCompleteListener(onCompleteListener);
    }

    public void addDataField(CabinViewModel totalIceBlocks, OnSuccessListener<? super Void> onSuccessListener, OnFailureListener error_writing_document) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_CABIN).document(totalIceBlocks.getCabinName()).set(totalIceBlocks.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }
}
