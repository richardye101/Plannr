package com.example.plannr.views;

public class OfferedCourse {
    String courseCode;
    String courseId;
    String courseName;
    String coursePrereq;

    public OfferedCourse(String courseCode, String courseId, String courseName, String coursePrereq) {
        this.courseCode = courseCode;
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePrereq = coursePrereq;
    }
}
