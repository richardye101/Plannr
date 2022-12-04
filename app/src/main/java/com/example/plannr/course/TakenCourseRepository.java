package com.example.plannr.course;

import java.util.ArrayList;

/**
 * Singleton access class that stores available Courses
 */

public class TakenCourseRepository {
    private static ArrayList<Course> courses;
    private static TakenCourseRepository takenCourseRepository;

    private TakenCourseRepository() {
    }

    public static TakenCourseRepository getInstance() {
        if(takenCourseRepository == null)
            takenCourseRepository = new TakenCourseRepository();
        return takenCourseRepository;
    }

    public static ArrayList<Course> getCourses() {
        if(courses == null)
            courses = new ArrayList<Course>();
        return courses;
    }

    public static void addCourse(Course course) {
        ArrayList<Course> courses = TakenCourseRepository.getCourses();
        if(!courses.contains(course)) {
            courses.add(course);
        } else {
            int index = courses.indexOf(course);
            courses.get(index).updateCourse(course);
        }
    }

    public static void removeCourse(Course course) {
        TakenCourseRepository.getCourses().remove(course);
    }
}
