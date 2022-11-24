package com.example.plannr.util;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.LoginFragment;
import com.example.plannr.R;
import com.example.plannr.RegisterFragment;
import com.example.plannr.models.AdminUser;
import com.example.plannr.models.StudentUser;
import com.example.plannr.models.User;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.auth.FirebaseAuth;

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
                                      EditText inputConfirmPassword, ProgressDialog pd) {
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

    public static boolean canAuthenticate(EditText inputEmail, EditText inputPassword,
                                          ProgressDialog pd) {
        if(validateEmail(inputEmail) && validatePassword(inputPassword)){
            pd.setMessage("Logging in...");
            pd.setTitle("Login");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            return true;
        }
        return false;
    }

    public static void register(EditText inputEmail, EditText inputName, EditText inputPassword,
                                ProgressDialog progressDialog, DatabaseConnection db,
                                FirebaseAuth mAuth, RegisterFragment rf){
        String email = inputEmail.getText().toString();
        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                User.createUserInDb(db, mAuth.getUid(), email, name);
                progressDialog.dismiss();
                NavHostFragment.findNavController(rf)
                        .navigate(R.id.action_registerFragment_to_loginFragment);
                Toast.makeText(rf.getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
            }
            else{
                progressDialog.dismiss();
                Toast.makeText(rf.getActivity(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
//                                +task.getException()
            }
        });
    }

    public static void login(EditText inputEmail, EditText inputPassword, ProgressDialog pd,
                             DatabaseConnection db, FirebaseAuth mAuth, LoginFragment lf){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                pd.dismiss();
                setUser(mAuth.getUid(), db, lf);
                NavHostFragment.findNavController(lf)
                        .navigate(R.id.action_loginFragment_to_FirstFragment);
                Toast.makeText(lf.getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
            }
            else{
                pd.dismiss();
                Toast.makeText(lf.getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
//                                +task.getException()
            }
        });
    }

    public static void setUser(String uid, DatabaseConnection db, LoginFragment lf){
        db.ref.child("users").child(uid).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                HashMap foundUser = (HashMap) task.getResult().getValue();
                if(foundUser != null){
                    if((boolean) foundUser.get("isAdmin")){
                        AdminUser.setAdminDetails(foundUser);
                        AdminUser admin = AdminUser.getInstance();
                        Log.e("setUser", "Set admin complete: " + admin.toString());
                    }
                    else{
                        StudentUser.setStudentDetails(foundUser);
                        StudentUser student = StudentUser.getInstance();
                        Log.e("setUser", "Set student complete: " + student.toString());
                    }
                }
                else{
                    Log.e("firebase", "No user retrieved", task.getException());
                    Toast.makeText(lf.getActivity(), "Retrieved user is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
