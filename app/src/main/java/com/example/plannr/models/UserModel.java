package com.example.plannr.models;

import android.util.Log;

import com.example.plannr.Contract;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * A class representing the model of our data and all code related to
 * a user.
 * The Model in MVP
 */
public class UserModel implements Contract.IUserModel{

    private String email;
    private String name;
    private boolean isAdmin;
    private UserModel user;

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

    public void setIsAdmin(boolean admin) {
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public void createUserInDb(DatabaseConnection db, String id, String email, String name) {
        UserModel curUserModel = new UserModel(email, name);
        db.ref.child("users").child(id).setValue(curUserModel);
    }
    @Override
    public void register(String email, String name, String password, String confirmPassword,
                         DatabaseConnection db, FirebaseAuth mAuth, Contract.IRegisterView rf){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            rf.hideLoadingRegister();

            if(task.isSuccessful()){
                createUserInDb(db, mAuth.getUid(), email, name);
                rf.registerSuccess();
            }
            else{
                rf.registerFailure();
            }
        });
    }

    @Override
    public void login(String email, String password,
                      DatabaseConnection db, FirebaseAuth mAuth, Contract.ILoginView lf){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            lf.hideLoadingLogin();

            if(task.isSuccessful()){
                setUserLocallyAndUpdateView(mAuth.getUid(), db, lf);
            }
            else{
                lf.loginFailure();
            }
        });
    }

    @Override
    public void setUserLocallyAndUpdateView(String uid, DatabaseConnection db, Contract.ILoginView lf){
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
                        setUser(admin);
                    }
                    else{
                        StudentUserModel.setStudentDetails(foundUser);
                        StudentUserModel student = StudentUserModel.getInstance();
                        Log.e("setUser", "Set student complete: " + student.toString());
                        setUser(student);
                    }
                    lf.loginSuccess(getUser());
                }
                else{
                    Log.e("firebase", "No user retrieved", task.getException());
                }
            }
        });
    }
}
