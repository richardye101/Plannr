package com.example.plannr.admin;

import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DatabaseReference;

public class AdminAddPresenter {
    private AdminAddView view;
    private DatabaseConnection db;

    public AdminAddPresenter(AdminAddView view){
        this.view = view;
        db = DatabaseConnection.getInstance();
    }

    public void addCourse(){
        //Get database ref
        DatabaseReference ref = db.ref;

        //Retrieve input info
        String courseCode = view.getCourseCode();
        String name = view.getCourseName();
        boolean fall = view.getFallAvailability();
        boolean winter = view.getWinterAvailability();
        boolean summer = view.getSummerAvailability();
        String prerequisites = view.getPrerequisite().replaceAll("\\s", "");

        //Get specific database ref
        DatabaseReference offerings = ref.child("offerings");

        //Create course object
        Course course = new Course(name, fall, winter, summer, prerequisites);

        //Add to database
        offerings.child(courseCode).setValue(course);

    }
}
