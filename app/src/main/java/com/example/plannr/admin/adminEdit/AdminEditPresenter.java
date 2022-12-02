package com.example.plannr.admin.adminEdit;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.example.plannr.admin.adminAdd.AdminAddFragment;
import com.example.plannr.admin.adminAdd.AdminAddPresenter;
import com.example.plannr.admin.adminAdd.FirebaseCallback;
import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AdminEditPresenter {
    private AdminEditFragment view;
    private DatabaseConnection db;
    private AdminAddPresenter helper;

    public AdminEditPresenter(AdminEditFragment view){
        this.view = view;
        db = DatabaseConnection.getInstance();
        helper = new AdminAddPresenter();
    }

    //method to edit course

    public void editCourse(String givenCourse){

        //get specific reference
        DatabaseReference ref = db.ref;
        DatabaseReference offerings = ref.child("offerings");

        //retrieve input info
        String courseCode = view.getEditCourseCode().replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        String courseName = view.getEditCourseName();
        boolean fall = view.getEditFallAvailability();
        boolean winter = view.getEditWinterAvailability();
        boolean summer = view.getEditSummerAvailability();
        String prerequisites = view.getEditPrerequisite().replaceAll("\\s", "").toUpperCase(Locale.ROOT);

        //check if valid
        if(inputValidator(courseCode, courseName, fall, winter, summer, prerequisites) == true){

            //get the given courses id
            helper.readData(new FirebaseCallback() {
                @Override
                public void onCallBack(HashMap<String, String> list) {

                    //convert input prerequisites into list
                    Course course1 = new Course();
                    ArrayList<String> givenPrerequisites = course1.stringToArraylist(prerequisites);
                    TextView warningText = view.getEditWarningText();

                    //insert the choice of no prerequisite
                    list.put("", "");

                    //check if prerequisites exist in database
                    int count = 0;

                    for(int i = 0; i < givenPrerequisites.size(); i++){
                        if(list.containsValue(givenPrerequisites.get(i))){
                            count ++;
                        }
                    }

                    if(count == givenPrerequisites.size()){
                        //Hide warning message
                        warningText.setTextColor(Color.WHITE);

                        //create prerequisite id
                        String idPrerequisites = "";

                        for(int i = 0; i < givenPrerequisites.size(); i++){
                            for(Map.Entry<String, String> set : list.entrySet()){
                                if(set.getValue().equals(givenPrerequisites.get(i)) && givenPrerequisites.get(i).equals("") == false){
                                    idPrerequisites =  idPrerequisites + "," + set.getKey();
                                }
                            }
                        }

                        Log.i("PREREQUISITE", idPrerequisites);

                        //get given course id
                        String id = "";

                        for(Map.Entry<String, String> set: list.entrySet()){
                            if(set.getValue().equals(givenCourse)){
                                id = id + set.getKey();
                            }
                        }

                        //create course object
                        Course course = new Course(courseCode, courseName, fall, winter, summer, idPrerequisites);

                        offerings.child(id).setValue(course);

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
        TextView warningText = view.getEditWarningText();

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

        //check for repeating prerequisites
        Course course = new Course();
        ArrayList<String> prereqList = course.stringToArraylist(prerequisites);
        Set<String> set = new HashSet<String>(prereqList);

        if(set.size() < prereqList.size()){
            warningText.setText("No repeating prerequisites!");
            warningText.setTextColor(Color.RED);
            return false;
        }

        return true;
    }

}
