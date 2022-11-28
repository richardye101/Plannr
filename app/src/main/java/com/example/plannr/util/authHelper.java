package com.example.plannr.util;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> stringToHashMap(String value){
        value = value.substring(1, value.length()-1);           //remove curly brackets
        String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
        Map<String,String> map = new HashMap<>();

        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split("=");                   //split the pairs to get key and value
            if(entry[0] == "isAdmin")
                map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
            else
                map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
        }
        return map;
    }
}
