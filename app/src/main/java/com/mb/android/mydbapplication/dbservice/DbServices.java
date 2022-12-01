package com.mb.android.mydbapplication.dbservice;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DbServices {

    FirebaseDatabase database;

    public DbServices(){
        database = FirebaseDatabase.getInstance();
    }

    public ArrayList<HashMap<String, String>> get(){
        String student;

        for (int i=1; i <= 10; i++) {
            HashMap <String, String> aStudent = new HashMap <>();
            aStudent.put("student"  + " Name", "name " + i);
            aStudent.put("student"  + " number", i * 200 + 25 + "");
            //stdent = database.getReference("CSCB07").child("Class list").child("Student " + i).getValue();

        }
        return new ArrayList<>();
    }

    public void save(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference DatabaseRef = database.getReference();

        HashMap<String, String> course = new HashMap <>();
        course.put("course title", "Object Orient Programming in Java");
        course.put("Professor", "Alex");
        DatabaseRef.child("cp 213").setValue(course);

        for (int i=1; i <= 10; i++) {
            HashMap <String, String> aStudent = new HashMap <>();
            aStudent.put("student"  + " Name", "name " + i);
            aStudent.put("student"  + " number", i * 200 + 25 + "");
            database.getReference("CSCB07").child("Class list").child("Student " + i).setValue(aStudent);
        }

        /*Random rand = new Random(); //instance of random class
        int upperbound = 10000;
        i = 1;
        for (; i <= 10; i++) {
            int id = rand.nextInt(upperbound);
            User user = new User("first_name " + id, "last_name" + id,
                    id, "user" + id + "@gmail.com");
            database.getReference("Users").child("User " + i).setValue(user);
        }*/
    }

}
