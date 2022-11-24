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
        String availability = view.getAvailability();
        String prerequisites = view.getPrerequisite();

        //Get specific database ref
        DatabaseReference offerings = ref.child("offerings");

        //Create course object
        Course course = new Course(name, availability, prerequisites);

        //Add to database
        offerings.child(courseCode).setValue(course);

    }
}
