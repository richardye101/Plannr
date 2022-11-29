package com.example.plannr.admin;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * FirebaseCallback interface that handles callbacks from database
 */

public interface FirebaseCallback {
    public void onCallBack(ArrayList<String> list);
}
