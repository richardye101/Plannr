package com.example.plannr.models;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

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

    public static void setAdminDetails(HashMap details){
        AdminUser admin = AdminUser.getInstance();
        admin.setEmail((String) details.get("email"));
        admin.setName((String) details.get("name"));
    }

    public String toString() {
        return "User{" +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                '}';
    }
//    viewOfAllCourses (so they can add and edit it)
}
