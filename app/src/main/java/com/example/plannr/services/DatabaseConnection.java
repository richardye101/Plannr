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

//db.ref.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
////            Log.d("stored users", String.valueOf(users));
////            Log.i("theUser", String.valueOf(users.get(User.generateID(username))));
//
//            authenticate(username, password, new LoginCallback() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//                        Log.e("firebase", "Error getting data", task.getException());
//                    }
//                    else {
//                        HashMap users = (HashMap) task.getResult().getValue();
//                        onItemsObtained(users);
//                        // Used to create a new user if their account is not found
//                        if(users.get(User.generateHash(username)) == null){
//                            User user = new User(username, hashPassword(password));
//                            Log.d("Adding new user", user.getUsername());
//                            db.ref.child("users").child(User.generateHash(username)).setValue(user);
//                        }
//                        Log.d("password check", String.valueOf(samePassword(username, password)));
//                public void isAuthenticated(boolean authed, User userObj) {
//                    if(authed){
//                        user = new LoggedInUser(
//                                java.util.UUID.randomUUID().toString(),
//                                userObj.getName());
//                        Log.d("set user", String.valueOf(user));
//                    }
//                }
//            });