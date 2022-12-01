package com.mb.android.mydbapplication.model;

public class Course {
    String courseTitle;
    String courseCode;

    public Course(String courseTitle, String courseCode) {
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseName) {
        this.courseTitle = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

}
