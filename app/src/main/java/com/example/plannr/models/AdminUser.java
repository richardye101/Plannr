package com.example.plannr.models;

import androidx.fragment.app.Fragment;

/**
 * A singleton class that is instantiated during login, if the user is an admin.
 */
public class AdminUser extends User{
//    identifies user as admin

    private static AdminUser user;
    public AdminUser(){
    }

    public AdminUser(String username, String name) {
        super(username, name);
    }
//    should have a list of
//    courseID's of courses they have taken

    public static AdminUser getInstance(){
        if(user == null){
            user = new AdminUser();
        }
        return user;
    }
//    viewOfAllCourses (so they can add and edit it)
}
