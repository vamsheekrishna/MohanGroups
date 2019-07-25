package com.example.mybusinesstracker.cloud_firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class TestDB extends DBInstance {

    public CollectionReference getCollection() {
        return DBInstance.getDBInstance().collection("");
    }
    public void addDataField(DocumentReference dr, HashMap<String, String> data) {
        //getCollection("testPav").add(data);
        //, OnFailureListener onFailureListener, OnSuccessListener<? super Void> onSuccessListener

        //dr.set(data).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFailureListener).addOnSuccessListener(onSuccessListener);

// Add a new document with a generated ID
        FirebaseFirestore.getInstance().collection("users")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("test", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error adding document", e);
                    }
                });

    }

    public void updateDataField(DocumentReference dr, HashMap<String, String> data) {

    }

    public void removeDataField(DocumentReference dr, String key) {

    }

    public DocumentReference getDocument(CollectionReference cr, String name) {
        return cr.document(name);
    }
}
