package com.example.plannr.admin.adminEdit;

public class User {
    private String email;
    private String id;
    private boolean isAdmin;
    private String taken;

    public User(String email, String id, boolean isAdmin, String taken) {
        this.email = email;
        this.id = id;
        this.isAdmin = isAdmin;
        this.taken = taken;
    }

    public User() {

    }

    public String getTaken(){
        return this.taken;
    }

    public boolean getIsAdmin(){
        return this.isAdmin;
    }
}
