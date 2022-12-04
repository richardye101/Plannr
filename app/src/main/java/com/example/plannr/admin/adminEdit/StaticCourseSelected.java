package com.example.plannr.admin.adminEdit;

public class StaticCourseSelected {
    static String courseCode;

    public void setCode(String code){
        courseCode = code;
    }

    public String getCourseCode(){
        return courseCode;
    }
}
