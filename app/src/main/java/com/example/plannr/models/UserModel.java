package com.example.plannr.models;

import android.util.Log;
import android.widget.EditText;

import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.views.ILoginView;
import com.example.plannr.views.IRegisterView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * A class representing the model of our data and all code related to
 * a user.
 * The Model in MVP
 */
public class UserModel {

    private String email;
    private String name;
    private boolean isAdmin;

    public UserModel(){
    }

    public UserModel(String email, String name){
        this.email = email;
        this.name = name;
        this.isAdmin = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return email.equals(userModel.email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public static void createUserInDb(DatabaseConnection db, String id, String email, String name) {
        UserModel curUserModel = new UserModel(email, name);
        db.ref.child("users").child(id).setValue(curUserModel);
    }

    public static void register(EditText inputEmail, EditText inputName, EditText inputPassword,
                                DatabaseConnection db, FirebaseAuth mAuth, IRegisterView rf){
        String email = inputEmail.getText().toString();
        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            rf.hideLoadingRegister();

            if(task.isSuccessful()){
                UserModel.createUserInDb(db, mAuth.getUid(), email, name);
                rf.registerSuccess();
            }
            else{
                rf.registerFailure();
            }
        });
    }

    public static void login(EditText inputEmail, EditText inputPassword,
                             DatabaseConnection db, FirebaseAuth mAuth, ILoginView lf){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            lf.hideLoadingLogin();

            if(task.isSuccessful()){
                setUser(mAuth.getUid(), db);
                lf.loginSuccess();
            }
            else{
                lf.loginFailure();
            }
        });
    }

    public static void setUser(String uid, DatabaseConnection db){
        db.ref.child("users").child(uid).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                HashMap foundUser = (HashMap) task.getResult().getValue();
                if(foundUser != null){
                    if((boolean) foundUser.get("isAdmin")){
                        AdminUserModel.setAdminDetails(foundUser);
                        AdminUserModel admin = AdminUserModel.getInstance();
                        Log.e("setUser", "Set admin complete: " + admin.toString());
                    }
                    else{
                        StudentUserModel.setStudentDetails(foundUser);
                        StudentUserModel student = StudentUserModel.getInstance();
                        Log.e("setUser", "Set student complete: " + student.toString());
                    }
                }
                else{
                    Log.e("firebase", "No user retrieved", task.getException());
                }
            }
        });
    }
}
