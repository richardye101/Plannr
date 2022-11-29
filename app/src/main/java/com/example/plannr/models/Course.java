package com.example.plannr.models;

/**
 * This class stores basic information about a course
 */

public class Course {
    private String name;
//    private String code;
//    private Course[] prerequisites;

//    public Course(String name, String code, Course[] prerequisites) {
//        this.name = name;
//        this.code = code;
//        this.prerequisites = prerequisites;
//    }

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public Course[] getPrerequisites() {
//        return prerequisites;
//    }
}
