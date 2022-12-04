package com.example.plannr.course;

import java.util.ArrayList;

/**
 * Singleton access class that stores available Courses
 */

public class CourseRepository {
    private static ArrayList<Course> courses;
    private static CourseRepository courseRepository;
    private static int selectedCourseId;

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

    public static Course getCourseById(int id) {
        for(Course course : courses) {
            if(course.getId() == id)
                return course;
        }
        return null;
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
        courses.remove(course);
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
