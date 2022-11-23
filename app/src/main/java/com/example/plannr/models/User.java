package com.example.plannr.models;

import com.example.plannr.services.DatabaseConnection;

public class User {

    private String email;
    private String name;

    public User(){
    }

    public User(String email, String name){
        this.email = email;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public static void createUserInDb(DatabaseConnection db, String id, String email, String name) {
        User curUser = new User(email, name);
        db.ref.child("users").child(id).setValue(curUser);
    }
}
