package com.example.plannr.course;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Course class containing a courses properties as well as data type conversion methods (String (db <---> Arraylist (local))
 */

public class Course {

    private  String courseCode;
    private String courseName;
    private String prerequisites;
    private boolean fall;
    private boolean winter;
    private boolean summer;

    public Course(String courseCode, String courseName, boolean fall, boolean winter, boolean summer, String prerequisites) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.fall = fall;
        this.winter = winter;
        this.summer = summer;
        this.prerequisites = prerequisites;
    }

    public Course() {

    }

    //getter and setter methods

    public void setCourseCode(String newCourseCode){ this.courseCode = newCourseCode;}

    public String getCourseCode(){return this.courseCode;}

    public void setCourseName(String newName){
        this.courseName = newName;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public void setFallAvailability(boolean newFallAvailability){
        this.fall = newFallAvailability;
    }

    public boolean getFallAvailability(){
        return this.fall;
    }

    public void setWinterAvailability(boolean newWinterAvailability){
        this.winter = newWinterAvailability;
    }

    public boolean getWinterAvailability(){
        return this.winter;
    }

    public void setSummerAvailability(boolean newSummerAvailability){
        this.summer = newSummerAvailability;
    }

    public boolean getSummerAvailability(){
        return this.summer;
    }

    public void setPrerequisites(String newPrerequisites){
        this.prerequisites = newPrerequisites;
    }

    public String getPrerequisites(){
        return this.prerequisites;
    }

    //method that converts the retrieved string of prerequisites from db to an arraylist

    public static ArrayList<String> stringToArraylist(String prerequisites){

        //convert to array of coursecodes
        String[] arrOfStr = prerequisites.split(",");

        //Add to arraylist
        ArrayList<String> prereqList = new ArrayList<String>();

        for(String a: arrOfStr){
            prereqList.add(a);
        }

        return prereqList;
    }

    //method that converts the ArrayList back to a string so it can be pushed to the db

    public String arraylistToString(ArrayList<String> prerequisites){

        //create accumulator string
        String prereqString = "";

        //add to string from arraylist
        for(int i = 0; i < prerequisites.size(); i++){
            if(i == prerequisites.size() - 1){
                prereqString = prereqString + prerequisites.get(i);
            }
            else {
                prereqString = prereqString + prerequisites.get(i) + ",";
            }
        }

        return prereqString;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Course)) {
            return false;
        }
        if(((Course) o).getCourseCode().equals(courseCode)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}
