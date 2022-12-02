package com.example.plannr.course;

public class CourseHash {
    Course course;
    String code;

    public CourseHash() {

    }

    public CourseHash(Course course, String code) {
        this.course = course;
        this.code = code;
    }

    public String getCourseHash() {
        return code;
    }

    public Course getCourse() {
        return course;
    }
}
