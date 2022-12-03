package com.example.plannr.course;

import com.google.firebase.database.DataSnapshot;

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

    public CourseHash getHash(Iterable<DataSnapshot> snap) {
        for(DataSnapshot i: snap) {
            if(course.getCourseCode().equals(i.getValue(Course.class).getCourseCode())) {
                return new CourseHash(course, i.getKey());
            }
        }
        return new CourseHash();
    }
}
