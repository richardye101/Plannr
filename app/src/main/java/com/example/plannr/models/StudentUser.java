package com.example.plannr.models;

import com.example.plannr.services.DatabaseConnection;
import java.util.ArrayList;

/**
 * A singleton class that is instantiated during login, if the user is a student.
 * Data used for our application is stored in this object.
 */
public class StudentUser extends User{

    private static StudentUser user;
    ArrayList<String> courses;

    public StudentUser(){
        courses = new ArrayList<String>();
    }

    public StudentUser(String username, String name) {
        super(username, name);
        courses = new ArrayList<String>();
    }
//    should have a list of
//    courseID's of courses they have taken

    public static StudentUser getInstance(){
        if(user == null){
            user = new StudentUser();
        }
        return user;
    }

    public static void createStudentDb(DatabaseConnection db, String id, String email, String name) {
        StudentUser student = new StudentUser(email, name);
        db.ref.child("students").child(id).setValue(student);
    }

    public void setTakenCourses(ArrayList<String> taken){
        if(taken != null){
            courses = (ArrayList<String>) taken.clone();
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", takenCourses='" + courses.toString() + '\'' +
                '}';
    }
}
