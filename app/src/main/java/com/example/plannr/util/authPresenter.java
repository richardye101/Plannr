package com.example.plannr.util;

import android.app.ProgressDialog;

import android.widget.EditText;

import com.example.plannr.LoginFragment;
import com.example.plannr.RegisterFragment;
import com.example.plannr.models.UserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.views.ILoginView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A fragment representing the authorization methods to perform the business logic of loggin in.
 * The Presenter in MVP
 */
public class authPresenter {

    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ILoginView mILoginView;

    public static boolean validateEmail(String email){
        if(!email.matches(emailPattern)){
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password){
        if(password.isEmpty() || password.length() < 6){
            inputPassword.setError("Password needs 6 or more characters");
            return false;
        }
        return true;
    }

    public static boolean matchPasswordRegister(String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            inputConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    public static void handleRegistration(EditText inputEmail, EditText inputName,
                                          EditText inputPassword, EditText inputConfirmPassword,
                                          ProgressDialog pd, DatabaseConnection db,
                                          FirebaseAuth mAuth, RegisterFragment rf) {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();
        if(!validateEmail(email)){
            ILoginView.showInvalidEmail();
            inputEmail.setError("Invalid Email");
        }
        if(validateEmail(email) &&
                validatePassword(password) &&
                matchPasswordRegister(password, confirmPassword)){
            pd.setMessage("Registering...");
            pd.setTitle("Registration");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            UserModel.register(inputEmail, inputName, inputPassword, pd, db,
                    mAuth, rf);
        }
    }

    public static void handleAuthentication(EditText inputEmail, EditText inputPassword,
                                            ProgressDialog pd, DatabaseConnection db,
                                            FirebaseAuth mAuth, LoginFragment lf) {
        if(validateEmail(inputEmail) && validatePassword(inputPassword)){
            pd.setMessage("Logging in...");
            pd.setTitle("Login");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            UserModel.login(inputEmail, inputPassword, pd,
                    db, mAuth, lf);
        }
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
