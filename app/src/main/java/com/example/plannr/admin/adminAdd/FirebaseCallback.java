package com.example.plannr.admin.adminAdd;

import java.util.HashMap;

/**
 * FirebaseCallback interface that handles callbacks from database
 */

public interface FirebaseCallback {
    public void onCallBack(HashMap<String, String> list);
}