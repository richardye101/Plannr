package com.example.plannr.students;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseCode;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class TableMaker {
    private ArrayList<CourseCode> table;
    private DatabaseConnection db;

    public TableMaker() {
        table = new ArrayList<CourseCode>();
        db = DatabaseConnection.getInstance();
    }

    public TableMaker(ArrayList<CourseCode> table) {
        this.table = table;
    }

    public void makeTable(ArrayList<CourseCode> available) {
        StudentUserModel student = StudentUserModel.getInstance();
        table.clear();
        ArrayList<String> prereqs = new ArrayList<>();

        //get list of all available courses --or simply use a for each loop if possible
        for(CourseCode i: available) {
            prereqs.clear();
            for(String j : i.getCourse().getPrerequisites().split(",")){
                prereqs.add(j);
            }
            if(student.getTakenCourses().contains(i.getCourse().getCourseName())) {
            } else if(i.getCourse().getPrerequisites().isEmpty() || student.getTakenCourses().containsAll(prereqs)) {
                table.add(i);
            }
        }
    }

    public ArrayList<CourseCode> listAvailable(Iterable<DataSnapshot> snap) {
        ArrayList<CourseCode> courses = new ArrayList<>();

        for(DataSnapshot i: snap) {
            Course course = i.getValue(Course.class);
            courses.add(new CourseCode(course, i.getKey()));

        }
        return courses;
    }

    //temporary
    @Override
    public String toString() {
        String s = "";
        for(CourseCode i: this.table) {
            s += i.getCourseCode() + ", ";
        }
        return s;
    }
}
