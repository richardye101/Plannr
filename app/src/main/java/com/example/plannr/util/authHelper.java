package com.example.plannr.util;

/**
 * A fragment representing the authorization methods to perform the business logic of authorization.
 * The Presenter in MVP
 */
public class authHelper {

    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean validateEmail(String email){
        if(!email.matches(emailPattern)){
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password){
        if(password.isEmpty() || password.length() < 6){
            return false;
        }
        return true;
    }

    public static boolean matchPasswordRegister(String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            return false;
        }
        return true;
    }

}
