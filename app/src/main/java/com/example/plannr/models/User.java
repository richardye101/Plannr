package com.example.plannr.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Objects;

public abstract class User {

//    public String id;
    private String email;
    private String name;

    public User(String email, String name){
//        this.id = id;
        this.email = email;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
