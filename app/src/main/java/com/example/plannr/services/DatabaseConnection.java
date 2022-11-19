package com.example.plannr.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// converted the database reference into it's own class inorder to allow us to switch
// databases whenever

public final class DatabaseConnection extends Service {
    public DatabaseReference ref;
    private static DatabaseConnection db;

    public DatabaseConnection() {
        //CREATE DATABASE REFERENCE
        ref = FirebaseDatabase.getInstance("https://plannr-8726a-default-rtdb.firebaseio.com/").getReference();
    }

    public static DatabaseConnection getInstance(){
        if(db == null){
            db = new DatabaseConnection();
        }
        return db;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}