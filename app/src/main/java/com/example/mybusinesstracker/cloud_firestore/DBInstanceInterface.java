package com.example.mybusinesstracker.cloud_firestore;

import com.google.firebase.firestore.CollectionReference;

public interface DBInstanceInterface {
    CollectionReference getCollection();
}
