package com.example.plannr.course;

import java.util.ArrayList;

/**
 * Singleton access class that stores available Courses
 */

public class CourseRepository {
    private static ArrayList<Course> courses;
    private static CourseRepository courseRepository;

    private CourseRepository() {
    }

    public static CourseRepository getInstance() {
        if(courseRepository == null)
            courseRepository = new CourseRepository();
        return courseRepository;
    }

    public static ArrayList<Course> getCourses() {
        if(courses == null)
            courses = new ArrayList<Course>();
        return courses;
    }

    public static void removeAllCourses() {
        courses = new ArrayList<Course>();
    }

    public static void addCourse(Course course) {
        ArrayList<Course> courses = CourseRepository.getCourses();
        if(!courses.contains(course)) {
            courses.add(course);
        } else {
            int index = courses.indexOf(course);
            courses.get(index).updateCourse(course);
        }
    }

    public static void removeCourse(Course course) {
        CourseRepository.getCourses().remove(course);
    }
}
