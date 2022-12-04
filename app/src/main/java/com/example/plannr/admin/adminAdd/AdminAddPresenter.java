package com.example.plannr.admin.adminAdd;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.R;
import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * AdminAddPresenter responsible for validating input as well as communicating with the database
 */

public class AdminAddPresenter{
    private AdminAddFragment view;
    private DatabaseConnection db;

    public AdminAddPresenter(AdminAddFragment view){
        this.view = view;
        db = DatabaseConnection.getInstance();
    }

    public AdminAddPresenter(){}

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
            //create id version of the course code prerequisites


            if(givenPrerequisites.isEmpty()){
                //Create course object
                Course finalCourse = new Course(courseCode, courseName, fall, winter, summer, "", courseCode.hashCode());
                addCourseToDb(offerings, courseCode, finalCourse);
            }
            else{
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
    //                        warningText.setTextColor(Color.GREEN);
    //                        warningText.setText("COURSE ADDED!");
                            Toast.makeText(view.getActivity(),
                                    "Course Added Successfully", Toast.LENGTH_SHORT).show();

                            String idPrerequisites = "";

                            for(int i = 0; i < givenPrerequisites.size(); i++){
                                for(Map.Entry<String, String> set : list.entrySet()){
                                    if(set.getValue().equals(givenPrerequisites.get(i)) && givenPrerequisites.get(i).equals("") == false){
                                        idPrerequisites =  idPrerequisites + "," + set.getKey();
                                    }
                                }
                            }

                            Log.i("PREREQUISITE", idPrerequisites);

                            Course finalCourse = new Course(courseCode, courseName, fall, winter, summer, idPrerequisites, courseCode.hashCode());

                            addCourseToDb(offerings, courseCode, finalCourse);
                        }
                        else {
                            //display error message
    //                        warningText.setTextColor(Color.RED);
                            if(list.containsValue(courseCode)) {
    //                            warningText.setText("THIS COURSE ALREADY EXISTS");
                                Toast.makeText(view.getActivity(),
                                        "Course Already Exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
    //                            warningText.setText("PREREQUISITE NOT LOGGED. ADD PREREQUISITE BEFORE CONTINUING!");
                                Toast.makeText(view.getActivity(),
                                        "Prerequisites do not exist. Add necessary prerequisites!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }
    }

    private void addCourseToDb(DatabaseReference offerings, String courseCode, Course finalCourse) {
        //Add to database
        offerings.child(String.valueOf(courseCode.hashCode())).setValue(finalCourse);

        NavHostFragment.findNavController(view)
                .navigate(R.id.action_adminAddFragment_to_DisplayCoursesFragment);
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
//            warningText.setText("You cannot have empty fields!");
//            warningText.setTextColor(Color.RED);
            Toast.makeText(view.getActivity(),
                    "You cannot have empty fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(fall == false && winter == false && summer == false){
//            warningText.setText("At least one checkbox must be selected!");
//            warningText.setTextColor(Color.RED);
            Toast.makeText(view.getActivity(),
                    "At least one session must be selected!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check if course codes are alphanumeric as we can assume they always should be
        if(courseCode.matches("[a-zA-Z0-9]+") == false ||  isAllComma(prerequisites) == true ){

//            warningText.setText("All course codes must be alphanumerical!");
//            warningText.setTextColor(Color.RED);
            Toast.makeText(view.getActivity(),
                    "All course codes must be alphanumerical!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(prerequisites.length() > 0 && prerequisites.matches("[a-zA-Z0-9,]+") == false){
//            warningText.setText("All course codes must be alphanumerical!");
//            warningText.setTextColor(Color.RED);
            Toast.makeText(view.getActivity(),
                    "All course codes must be alphanumerical!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check for repeating prerequisites
        Course course = new Course();
        ArrayList<String> prereqList = course.stringToArraylist(prerequisites);
        Set<String> set = new HashSet<String>(prereqList);

        if(set.size() < prereqList.size()){
//            warningText.setText("No repeating prerequisites!");
//            warningText.setTextColor(Color.RED);
            Toast.makeText(view.getActivity(),
                    "Error: Repeating prerequisites", Toast.LENGTH_SHORT).show();
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
                }
                else{
                    Toast.makeText(view.getActivity(),
                            "This is the first course! You neet to add any prerequisite courses first", Toast.LENGTH_SHORT).show();
                }
                firebaseCallback.onCallBack(dbCourses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error: ", error.getMessage());
            }

        });
    }
}
