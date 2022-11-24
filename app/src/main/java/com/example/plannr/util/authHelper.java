package com.example.plannr.util;

/**
 * A fragment representing the authorization methods to perform the business logic of authorization.
 * The Presenter in MVP
 */
public class authHelper {

    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean validateEmail(String email){
        return email.matches(emailPattern);
    }

    public static boolean validatePassword(String password){
        return !(password.isEmpty() || password.length() < 6);
    }

    public static boolean matchPasswordRegister(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

}
