package com.example.plannr.course;

import java.util.ArrayList;

/**
 * Singleton access class that stores available Courses
 */

public class TakenCourseRepository {
    private static ArrayList<Course> courses;
    private static TakenCourseRepository takenCourseRepository;
    private static int selectedCourseId;

    private TakenCourseRepository() {
    }

    public static TakenCourseRepository getInstance() {
        if(takenCourseRepository == null)
            takenCourseRepository = new TakenCourseRepository();
        return takenCourseRepository;
    }

    public static Course getCourseById(int id) {
        for(Course course : courses) {
            if(course.getId() == id)
                return course;
        }
        return null;
    }

    public static void removeAllCourses() {
        courses = new ArrayList<Course>();
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

    public static void setSelectedCourseId(int id) {
        selectedCourseId = id;
    }

    public static int getSelectedCourseId() {
        return selectedCourseId;
    }

    public static Course getSelectedCourse() {
        return getCourseById(selectedCourseId);
    }
}