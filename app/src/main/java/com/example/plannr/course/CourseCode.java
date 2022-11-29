package com.example.plannr.course;

public class CourseCode {
    Course course;
    String code;

    public CourseCode() {

    }

    public CourseCode(Course course, String code) {
        this.course = course;
        this.code = code;
    }

    public String getCourseCode() {
        return code;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return code;
    }
}
