package com.example.plannr.models;

import android.text.TextUtils;
import android.util.Log;

import com.example.plannr.course.TakenCourseRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A singleton class that is instantiated during login, if the user is a student.
 * Data used for our application is stored in this object.
 */
public class StudentUserModel extends UserModel {

    private static StudentUserModel user;
    List<String> courses;

    public StudentUserModel(){
        courses = new ArrayList<>();
    }

    public StudentUserModel(String username, String name) {
        super(username, name);
        courses = new ArrayList<>();
    }
//    should have a list of
//    courseID's of courses they have taken

    public static StudentUserModel getInstance(){
        if(user == null){
            user = new StudentUserModel();
        }
        return user;
    }

    public void setTakenCourses(String taken){
        if(taken != null){
            List<String> myList = new ArrayList<String>(Arrays.asList(taken.split(";")));
            courses = myList;
        }
    }

    public void removeTakenCourses(){
        courses = new ArrayList<>();
    }

    public String getTakenCourses() {
        return TextUtils.join(";", courses);
    }

    public List<String> getTakenCoursesList(){
        return courses;
    }

    public void setStudentDetails(Map<String, String> details){
        setEmail((String) details.get("email"));
        setName((String) details.get("name"));
        removeTakenCourses();
        Log.d("taken courses", "" + details.get("taken"));
        setTakenCourses(details.get("taken"));
    }

    public String toString() {
        return "Student{" +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", isAdmin='" + this.getIsAdmin() + '\'' +
                ", takenCourses='" + this.courses.toString() + '\'' +
                '}';
    }
}
