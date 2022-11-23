package com.example.plannr.models;

import com.example.plannr.services.DatabaseConnection;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void setTakenCourses(ArrayList<String> taken){
        if(taken != null){
            courses = (ArrayList<String>) taken.clone();
        }
    }
    public static void setStudentDetails(HashMap details){
        StudentUser student = StudentUser.getInstance();
        student.setEmail((String) details.get("email"));
        student.setName((String) details.get("name"));
        student.setTakenCourses((ArrayList<String>) details.get("taken"));
    }

    public String toString() {
        return "Student{" +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", takenCourses='" + this.courses.toString() + '\'' +
                '}';
    }
}
