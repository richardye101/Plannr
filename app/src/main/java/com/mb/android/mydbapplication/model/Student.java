package com.mb.android.mydbapplication.model;

import java.util.ArrayList;

public class Student {
    String studentName;
    ArrayList<Course> coursesChosen;

    public Student(String name, String studentNum, String grade, ArrayList<Course> coursesChosen) {
        this.studentName = name;
        this.coursesChosen = coursesChosen;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public ArrayList<Course> getCoursesChosen() {
        return coursesChosen;
    }

    public void setCoursesChosen(ArrayList<Course> coursesChosen) {
        this.coursesChosen = coursesChosen;
    }
}
