package com.example.plannr.students;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TableMakerPresenter {
    private TableMaker table;
    private DatabaseConnection db;

    TableMakerPresenter() {
        table = new TableMaker();
        db = DatabaseConnection.getInstance();
    }

    public void generateTable(Course course) {
        DatabaseReference refe = db.ref;
        DatabaseReference offerings = refe.child("offerings");
        offerings.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    table.getWhatTake(TableMaker.getHash(course, task.getResult().getChildren()), table.listAvailable(task.getResult().getChildren()));
                    needTake(table.buildTable(2022));
                    //actual view here
                }
            }
        });
    }

    public String needTake(ArrayList<String> need) {
        String test = "";
        for(String s : need) {
            test = test + s + " ";
        }
        return test;
    }
}
