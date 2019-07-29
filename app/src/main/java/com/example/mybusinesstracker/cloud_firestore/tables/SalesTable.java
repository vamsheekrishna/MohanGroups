package com.example.mybusinesstracker.cloud_firestore.tables;

import com.example.mybusinesstracker.cloud_firestore.DBInstance;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

public class SalesTable extends DBInstance {

    public CollectionReference getCollection() {
        return DBInstance.getDBInstance().collection(BASE_COLLECTION_FACTORY);
    }
    public void addDataField(SalesViewModel data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {

        getCollection().document(BASE_DIRECTORY_DETAILS)
        //String.valueOf(data.getDate())
                .collection(BASE_DIRECTORY_SALES).document(String.format("%05d", data.getDate())).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);

    }

    public void updateFields(Customer data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_CUSTOMER).document(data.getCustomerName()).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }
    public void deleteRecord(SalesViewModel data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_CUSTOMER).document(String.valueOf(data.getDate())).delete().addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);;
    }

    public void getSalesList(OnCompleteListener<QuerySnapshot> onCompleteListener, OnFailureListener onFailure, String startDate, String endDate) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_SALES)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .get().addOnCompleteListener(onCompleteListener).addOnFailureListener(onFailure);
    }
}
