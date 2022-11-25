package com.example.plannr.admin;

public class Course {

    private String courseName;
    private String availability;
    private String prerequisites;

    public Course(String courseName, String availability, String prerequisites){
        this.courseName = courseName;
        this.availability = availability;
        this.prerequisites = prerequisites;
    }

    public void setCourseName(String newName){
        this.courseName = newName;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public void setAvailability(String newAvailability){
        this.availability = newAvailability;
    }

    public String getAvailability(){
        return this.availability;
    }

    public void setPrerequisites(String newPrerequisites){
        this.prerequisites = newPrerequisites;
    }

    public String getPrerequisites(){
        return this.prerequisites;
    }
}

//TO DO:

// CHANGE AVAILABILITY AND PREREQ INTO ARRAYLISTS AND ADD (ADD OR REMOVE) FUNCTIONS SO MATTHEW CAN USE IT
// FOLLOW MVP STRUCTURE
//PRESENTER TAKES CARE OF VALID INPUT, CHECK IS PREREQUISITES ARE ALREADY IN, AND PUSHING TO DATABASE IN STRING VERSION
