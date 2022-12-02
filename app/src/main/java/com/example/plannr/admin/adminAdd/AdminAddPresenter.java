package com.example.plannr.admin.adminAdd;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * AdminAddPresenter responsible for validating input as well as communicating with the database
 */

public class AdminAddPresenter{
    private adminAddFragment view;
    private DatabaseConnection db;

    public AdminAddPresenter(adminAddFragment view){
        this.view = view;
        db = DatabaseConnection.getInstance();
    }

    //method that adds course to the db

    public void addCourse(){
        //Get database ref
        DatabaseReference ref = db.ref;

        //Retrieve input info
        String courseCode = view.getCourseCode().replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        String courseName = view.getCourseName();
        boolean fall = view.getFallAvailability();
        boolean winter = view.getWinterAvailability();
        boolean summer = view.getSummerAvailability();
        String prerequisites = view.getPrerequisite().replaceAll("\\s", "").toUpperCase(Locale.ROOT);

        //Get specific database ref
        DatabaseReference offerings = ref.child("offerings");

        //check if inputs are valid
        if(inputValidator(courseCode, courseName, fall, winter, summer, prerequisites) == true) {

            //initialize course, convert string prerequisites to an arraylist, get warning text ref
            Course course = new Course();
            ArrayList<String> givenPrerequisites = course.stringToArraylist(prerequisites);
            TextView warningText = view.getWarningText();


            //get database snapshot and compare
            readData(new FirebaseCallback() {
                @Override
                public void onCallBack(HashMap<String, String> list) {

                    //insert the choice of no prerequisite
                    list.put("", "");

                    //check if prerequisites exist in database
                    int count = 0;

                    for(int i = 0; i < givenPrerequisites.size(); i++){
                        if(list.containsValue(givenPrerequisites.get(i))){
                            count ++;
                        }
                    }

                    if(count == givenPrerequisites.size() && list.containsValue(courseCode) == false){
                        //Hide warning message
                        warningText.setTextColor(Color.WHITE);

                        //create id version of the course code prerequisites
                        String idPrerequisites = "";

                        for(int i = 0; i < givenPrerequisites.size(); i++){
                            for(Map.Entry<String, String> set : list.entrySet()){
                                if(set.getValue().equals(givenPrerequisites.get(i)) && givenPrerequisites.get(i).equals("") == false){
                                    idPrerequisites =  idPrerequisites + "," + set.getKey();
                                }
                            }
                        }

                        Log.i("PREREQUISITE", idPrerequisites);

                        //Create course object
                        Course finalCourse = new Course(courseCode, courseName, fall, winter, summer, idPrerequisites);

                        //Add to database
                        offerings.child(String.valueOf(courseCode.hashCode())).setValue(finalCourse);
                    }
                    else {
                        //display error message
                        warningText.setTextColor(Color.RED);
                        if(list.containsValue(courseCode)) {
                            warningText.setText("THIS COURSE ALREADY EXISTS");
                        }
                        else {
                            warningText.setText("PREREQUISITE NOT LOGGED. ADD PREREQUISITE BEFORE CONTINUING!");
                        }
                    }
                }
            });
        }
    }

    //method to check is input is valid

    public boolean isAllComma(String prerequisites){

        int tracker = 0;
        if(prerequisites.length() > 0) {
            for (int i = 0; i < prerequisites.length(); i++) {
                if (prerequisites.charAt(i) != ',') {
                    tracker++;
                }
            }
            if (tracker > 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean inputValidator(String courseCode, String courseName, boolean fall, boolean winter, boolean summer, String prerequisites){

        //Initialize warning text reference
        TextView warningText = view.getWarningText();

        //when it is empty
        if(courseCode.length() == 0 || courseName.replaceAll("\\s", "").length() == 0){
            warningText.setText("You cannot have empty fields!");
            warningText.setTextColor(Color.RED);
            return false;
        }
        if(fall == false && winter == false && summer == false){
            warningText.setText("At least one checkbox must be selected!");
            warningText.setTextColor(Color.RED);
            return false;
        }

        //check if course codes are alphanumeric as we can assume they always should be
        if(courseCode.matches("[a-zA-Z0-9]+") == false ||  isAllComma(prerequisites) == true ){

            warningText.setText("All course codes must be alphanumerical!");
            warningText.setTextColor(Color.RED);
            return false;
        }

        if(prerequisites.length() > 0 && prerequisites.matches("[a-zA-Z0-9,]+") == false){
            warningText.setText("All course codes must be alphanumerical!");
            warningText.setTextColor(Color.RED);
            return false;
        }

        return true;
    }

    //method that reads the course keys from database

    public void readData(FirebaseCallback firebaseCallback){

        //Configure database path and text reference
        DatabaseReference ref = db.ref.child("offerings");

        HashMap<String, String> dbCourses = new HashMap<String, String>();

        //Scrape the available from database
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot d : snapshot.getChildren()){
                        Course course = d.getValue(Course.class);
                        Log.i("COURSE CODE", course.getCourseCode());
                        dbCourses.put(d.getKey(), course.getCourseCode());
                    }
                    firebaseCallback.onCallBack(dbCourses);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error: ", error.getMessage());
            }

        });
    }
}
