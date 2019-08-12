package com.example.mybusinesstracker.cloud_firestore.tables;

import com.example.mybusinesstracker.cloud_firestore.DBInstance;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

public class CabinTable extends DBInstance {
    public CollectionReference getCollection() {
        return DBInstance.getDBInstance().collection(BASE_COLLECTION_FACTORY);
    }
    public void addDataField(Customer data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {

        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_CUSTOMER).document(data.getCustomerName()).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);

    }

    public void updateFields(Customer data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_CUSTOMER).document(data.getCustomerName()).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }
    public void deleteRecord(Customer data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_CUSTOMER).document(data.getCustomerName()).delete().addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }

    public void getCustomerList(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_CUSTOMER).get().addOnCompleteListener(onCompleteListener);
    }
}
