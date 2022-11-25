package com.example.plannr.services;

import com.example.plannr.models.Course;

import java.util.ArrayList;

public class CourseRepository {
    private ArrayList<Course> courses;
    private static CourseRepository courseRepository;

    private CourseRepository() {
    }

    public static CourseRepository getInstance() {
        if(courseRepository == null)
            courseRepository = new CourseRepository();
        return courseRepository;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}
