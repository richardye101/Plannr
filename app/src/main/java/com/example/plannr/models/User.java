package com.example.plannr.models;

import java.util.Objects;

public class User {

    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin && username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public static String generateHash(String username){
//        String admin;
//        if(isAdmin)
//            admin = "Admin";
//        else
//            admin = "";
        return String.valueOf(username.hashCode());
    }
    //    need a function for createUser
//    need a function for getting the hashed password
//    need a function for getting the username

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
