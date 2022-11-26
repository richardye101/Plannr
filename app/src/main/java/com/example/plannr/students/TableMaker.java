package com.example.plannr.students;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.course.Course;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TableMaker {
    private ArrayList<Course> table;
    private DatabaseConnection db;

    public TableMaker() {
        table = new ArrayList<Course>();
        db = DatabaseConnection.getInstance();
    }

    public TableMaker(ArrayList<Course> table) {
        this.table = table;
    }

    //
    //we can also just make this return a list or something instead of displaying it
    //TODO: Implement algorithm
    public void makeTable(StudentUserModel student) {
        ArrayList<Course> available = listAvailable();
        table.clear();

        //get list of all available courses --or simply use a for each loop if possible
        for(Course i: available) {
            if(student.getTakenCourses().contains(i.getCourseName())) {
            } else if(i.getCourseName().isEmpty() || student.getTakenCourses().containsAll(i.getPrerequisites())) {
                table.add(i);
            }
        }
        /*
        for(each course in the list)
            if(course name exists in student profile as taken) --possible use of helper function
                continue;
            else if(course has no prequisites or all prequisates are in student profile as taken) --possible use of helper function
                add to table list
            continue;
         */

        //display the table
    }

    //ignore this function/method for now
    //it is a mash of 2 different methods, I just combined them for now as I have not yet set
    //up where I will be putting the functions/methods
    public ArrayList<Course> listAvailable() {
        //make List of available courses
        DatabaseReference ref = db.ref;
        DatabaseReference offerings = ref.child("offerings");
        Object ob = new Object();

        //we may change this to a list of courses instead of list of strings later
        ArrayList<Course> courses = new ArrayList<Course>();

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    // this is a part of another function, ignore for now
                    for(DataSnapshot i: task.getResult().getChildren()) {
                        courses.add(i.getValue(Course.class)); //courses is defined in branch AdminAdd, as of writing this code it is not finished
                        //hence why it is not in this branch yet.

                    }
                }
                //return courses
            }
        });

        return courses;
    }

    public void displayTable(TableMaker t) {
        //display table

        //may be moved to a different class to adhere to SRP
    }
}
