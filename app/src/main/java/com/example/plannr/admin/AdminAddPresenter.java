package com.example.plannr.admin;

import android.graphics.Color;
import android.widget.TextView;

import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DatabaseReference;

/**
 * AdminAddPresenter responsible for validating input as well as communicating with the database
 */

public class AdminAddPresenter {
    private AdminAddView view;
    private DatabaseConnection db;

    public AdminAddPresenter(AdminAddView view){
        this.view = view;
        db = DatabaseConnection.getInstance();
    }

    //method that adds course to the db

    public void addCourse(){
        //Get database ref
        DatabaseReference ref = db.ref;

        //Retrieve input info
        String courseCode = view.getCourseCode();
        String courseName = view.getCourseName();
        boolean fall = view.getFallAvailability();
        boolean winter = view.getWinterAvailability();
        boolean summer = view.getSummerAvailability();
        String prerequisites = view.getPrerequisite().replaceAll("\\s", "");

        //Get specific database ref
        DatabaseReference offerings = ref.child("offerings");

        //check if inputs are valid

        if(inputValidator(courseCode, courseName, fall, winter, summer, prerequisites) == true) {

            //Hide warning message
            TextView warningText = view.getWarningText();
            warningText.setTextColor(Color.WHITE);

            //Create course object
            Course course = new Course(courseName, fall, winter, summer, prerequisites);

            //Add to database
            offerings.child(courseCode).setValue(course);
        }

    }

    //method to check is input is valid

    public boolean inputValidator(String courseCode, String courseName, boolean fall, boolean winter, boolean summer, String prerequisites){

        //Initialize warning text referance
        TextView warningText = view.getWarningText();

        //when it is empty
        if(courseCode.replaceAll("\\s", "").length() == 0 || courseName.replaceAll("\\s", "").length() == 0 || prerequisites.replaceAll("\\s", "").length() == 0){
            warningText.setText("You cannot have empty fields!");
            warningText.setTextColor(Color.RED);
            return false;
        }
        if(fall == false && winter == false && summer == false){
            warningText.setText("At least one checkbox must be selected!");
            warningText.setTextColor(Color.RED);
            return false;
        }

        //when prerequisites has only "," in it
        if(prerequisites.replaceAll("\\s", "") == ","){
            warningText.setText("Prerequisites has to be composed of class codes");
            warningText.setTextColor(Color.RED);
            return false;
        }

        return true;
    }
}
