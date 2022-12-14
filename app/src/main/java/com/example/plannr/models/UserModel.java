package com.example.plannr.models;

import android.util.Log;

import com.example.plannr.Contract;
import com.example.plannr.course.TakenCourseRepository;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.authHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * A class representing the model of our data and all code related to
 * a user.
 * The Model in MVP
 */
public class UserModel implements Contract.IUserModel{

    private String id;
    private String email;
    private String name;
    private boolean isAdmin;
    private DatabaseConnection db;
    private FirebaseAuth mAuth;
//    private FirebaseUser mUser;
    private Contract.IRegisterView rf;
    private Contract.ILoginView lf;

    private static UserModel curUser;

    public UserModel(){
    }

    public UserModel(String email, String name){
        this.email = email;
        this.name = name;
    }

    public static UserModel getInstance(){
        if(curUser == null){
            curUser = new UserModel();
        }
        return curUser;
    }
    public void setAuth(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }
    public void setDb(DatabaseConnection db){this.db = db;}
    public void registerUserSetup(Contract.IRegisterView rf){
        this.isAdmin = false;
        this.rf = rf;
    }

    public void loginUserSetup(Contract.ILoginView lf){
        this.isAdmin = false;
        this.lf = lf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return email.equals(userModel.email);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public Contract.IUserModel returnCurUser() {
        return curUser;
    }

    @Override
    public void createLoggedInUser(String email, String name, boolean isAdmin, String id) {
        UserModel user = UserModel.getInstance();
        user.setEmail(email);
        user.setName(name);
        user.setIsAdmin(isAdmin);
        user.setId(id);
    }

    @Override
    public void createUserInDb(String id, String email, String name) {
        UserModel curUserModel = UserModel.getInstance();
        curUserModel.createLoggedInUser(email, name, false, id);
        db.ref.child("users").child(id).setValue(curUserModel);
    }
    @Override
    public void register(String email, String name, String password, String confirmPassword){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            rf.hideLoadingRegister();

            if(task.isSuccessful()){
                createUserInDb(mAuth.getUid(), email, name);
                rf.registerSuccess();
            }
            else{
                rf.registerFailure();
            }
        });
    }

    @Override
    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            lf.hideLoadingLogin();
            if(task.isSuccessful()){
                setUserLocallyAndUpdateView(mAuth.getUid());
            }
            else{
                lf.loginFailure();
            }
        });
    }

    @Override
    public void setUserLocallyAndUpdateView(String uid){
        db.ref.child("users").child(uid).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("got user", String.valueOf(task.getResult().getValue()));
                Map<String, String> foundUser = authHelper.stringToHashMap(String.valueOf(task.getResult().getValue()));
                if(!foundUser.isEmpty()){
                    if(Boolean.parseBoolean(foundUser.get("isAdmin"))){
                        AdminUserModel admin = AdminUserModel.getInstance();
                        admin.setAdminDetails(foundUser);
                        admin.setId(uid);
                        Log.e("setUser", "Set admin complete: " + admin.toString());
                        createLoggedInUser(admin.getEmail(), admin.getName(), admin.getIsAdmin(), admin.getId());
                    }
                    else{
                        StudentUserModel student = StudentUserModel.getInstance();
                        student.setStudentDetails(foundUser);
                        student.setId(uid);
                        TakenCourseRepository.removeAllCourses();
                        Log.e("setUser", "Set student complete: " + student.toString());
                        createLoggedInUser(student.getEmail(), student.getName(), student.getIsAdmin(), student.getId());
                    }
                    lf.loginSuccess(UserModel.getInstance().getName());
                }
                else{
                    Log.e("firebase", "No user retrieved", task.getException());
                    lf.loginUserNotFound();
                }
            }
        });
    }
}
