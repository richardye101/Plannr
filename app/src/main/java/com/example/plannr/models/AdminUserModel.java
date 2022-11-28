package com.example.plannr.models;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class that is instantiated during login, if the user is an admin.
 */
public class AdminUserModel extends UserModel {
//    identifies user as admin

    private static AdminUserModel user;
    public AdminUserModel(){
    }

    public AdminUserModel(String username, String name) {
        super(username, name);
    }
//    should have a list of
//    courseID's of courses they have taken

    public static AdminUserModel getInstance(){
        if(user == null){
            user = new AdminUserModel();
        }
        return user;
    }

    public void setAdminDetails(Map<String, String> details){
//        AdminUserModel admin = AdminUserModel.getInstance();
        setEmail(details.get("email"));
        setName(details.get("name"));
    }

    public String toString() {
        return "User{" +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                '}';
    }
//    viewOfAllCourses (so they can add and edit it)
}
