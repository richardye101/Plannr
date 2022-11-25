package com.example.plannr.students;

import java.util.ArrayList;

//we can make it extend some other base account class later if we want to
public class Student {
    ArrayList<String> taken;

    public Student() {
        taken = new ArrayList<String>();
    }

    public Student(ArrayList<String> taken) {
        this.taken = taken;
    }

    //TODO: add final implementation and/or adjust algorimths

    //TODO: add exception, add database implementation
    public void add(String course) {
        if (taken.contains(course)) {
            //throw an exception
        }
        taken.add(course);
        //update database of new list
    }

    //TODO: add exception, add database implementation
    //throw an exception
    public void remove(String course) {
        if (!taken.contains(course)) {
            //throw an exception
        }
        taken.remove(course);
    }
}
