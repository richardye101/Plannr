package com.example.plannr.models;

/**
 * This class stores basic information about a course
 */

public class Course {
    private String name;
    private String code;
    private String[] prerequisites;
    private boolean fall;
    private boolean summer;
    private boolean winter;

    public Course(String name, String code, String[] prerequisites, boolean fall, boolean summer,
                  boolean winter) {
        this.name = name;
        this.code = code;
        this.prerequisites = prerequisites;
        this.fall = fall;
        this.summer = summer;
        this.winter = winter;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String[] getPrerequisites() {
        return prerequisites;
    }

    public boolean getFallAvailablility() {
        return fall;
    }

    public boolean getSummerAvailablility() {
        return summer;
    }

    public boolean getWinterAvailablility() {
        return winter;
    }
}
