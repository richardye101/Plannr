package com.example.plannr.models;

import java.util.Objects;

public class User {

    private final String password;
    private String username;
    private String name;
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

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
