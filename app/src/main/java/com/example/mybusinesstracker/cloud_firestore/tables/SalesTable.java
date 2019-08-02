package com.example.mybusinesstracker.cloud_firestore.tables;

import com.example.mybusinesstracker.cloud_firestore.DBInstance;
import com.example.mybusinesstracker.viewmodels.SalesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class SalesTable extends DBInstance {

    public CollectionReference getCollection() {
        return DBInstance.getDBInstance().collection(BASE_COLLECTION_FACTORY);
    }
    public void addDataField(SalesViewModel data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {

        /*getCollection().document(BASE_DIRECTORY_DETAILS)
        //String.format("%05d", data.getDate())
                .collection(BASE_DIRECTORY_SALES).document(String.valueOf(data.getDate())).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);*/
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_SALES).document(String.valueOf(data.getYear()))
                .collection(String.valueOf(data.getMonth())).document(String.valueOf(data.getDay()))
                .collection(data.getCabinID()).document(String.valueOf(data.getDate()))
                .set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
        //String.valueOf(data.getDay())
    }

    public void updateFields(SalesViewModel data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_SALES).document(String.valueOf(data.getDate())).set(data.getHashMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }
    public void deleteRecord(SalesViewModel data, OnFailureListener error_writing_document, OnSuccessListener<Void> onSuccessListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_SALES).document(String.valueOf(data.getDate())).delete().addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(error_writing_document);
    }

    public void getSalesList(OnCompleteListener<QuerySnapshot> onCompleteListener, OnFailureListener onFailure, String startDate, String endDate) {
        getCollection().document(BASE_DIRECTORY_DETAILS).collection(BASE_DIRECTORY_SALES)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailure);
    }
    public void getDaySales(Calendar calendar, String cabin_id, OnCompleteListener<QuerySnapshot> onCompleteListener, OnFailureListener onFailure) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_SALES).document(String.valueOf(calendar.get(Calendar.YEAR)))
                .collection(String.valueOf(calendar.get(Calendar.MONTH))).document(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))
                .collection(cabin_id).get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailure);
    }
    public void getMonthSales(Calendar calendar, OnCompleteListener<QuerySnapshot> onCompleteListener, OnFailureListener onFailure) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_SALES).document(String.valueOf(calendar.get(Calendar.YEAR)))
                .collection(String.valueOf(calendar.get(Calendar.MONTH))).get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailure);
    }
    public void getYearSales(Calendar calendar, OnFailureListener onFailure, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        getCollection().document(BASE_DIRECTORY_DETAILS)
                .collection(BASE_DIRECTORY_SALES).document(String.valueOf(calendar.get(Calendar.YEAR)))
                .get().addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailure);
    }
}
