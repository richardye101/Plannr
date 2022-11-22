package com.example.plannr.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentUser extends User{

    ArrayList<String> courses;

    public StudentUser(String username, String name) {
        super(username, name);
    }
//    should have a list of
//    courseID's of courses they have taken

    public static void createStudentDb(DatabaseConnection db, String id, String email, String name) {
        StudentUser student = new StudentUser(email, name);
        db.ref.child("students").child(id).setValue(student);
    }

}
