package com.example.plannr.util;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.plannr.models.User;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class auth {

    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean validateEmail(EditText inputEmail){
        String email = inputEmail.getText().toString();
        if(!email.matches(emailPattern)){
            inputEmail.setError("Invalid Email");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(EditText inputPassword){
        String password = inputPassword.getText().toString();
        if(password.isEmpty() || password.length() < 6){
            inputPassword.setError("Password needs 6 or more characters");
            return false;
        }
        return true;
    }

    public static boolean matchPasswordRegister(EditText inputPassword, EditText inputConfirmPassword){
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();
        if(!password.equals(confirmPassword)){
            inputConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    public static boolean canRegister(EditText inputEmail, EditText inputPassword,
                                      EditText inputConfirmPassword, ProgressDialog pd,
                                      DatabaseConnection db, FirebaseAuth mAuth) {
        if(validateEmail(inputEmail) &&
                validatePassword(inputPassword) &&
                matchPasswordRegister(inputPassword, inputConfirmPassword)){
            pd.setMessage("Registering...");
            pd.setTitle("Registration");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            return true;
        }
        return false;
    }

    public static boolean canAuthenticate(EditText inputEmail, EditText inputPassword, ProgressDialog pd,
                                       DatabaseConnection db, FirebaseAuth mAuth) {
        if(validateEmail(inputEmail) && validatePassword(inputPassword)){
            pd.setMessage("Logging in...");
            pd.setTitle("Login");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            return true;
        }
        return false;
    }

//    public static void onUsersObtained(HashMap users) {
////        users.clear(); // To remove old Data
//        this.users.putAll(users);
//        Log.d("stored users", String.valueOf(users));
//        // Continue your own logic...
//    }

//    public static User getUser(String username){
//        HashMap userMap = (HashMap) users.get(User.generateHash(username));
////        Log.d("theUserClass", String.valueOf(users.get(User.generateHash(username))));
//        User user = new User((String) userMap.get("username"), (String) userMap.get("password"));
//        if(user == null){
//            Log.e("user error", "No user found");
//            return null;
//        }
//        return user;
//    }

//    private static boolean samePassword(String username, String password){
//        User user = getUser(username);
//        String hash = hashPassword(password);
//
//        Log.d("entered password", String.valueOf(hash));
//        Log.d("stored password", String.valueOf(user.getPassword()));
//
//        if(hash.equals(user.getPassword())){
//            return true;
//        }
//        return false;
//    }
}
