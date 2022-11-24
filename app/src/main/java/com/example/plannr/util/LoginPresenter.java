package com.example.plannr.util;

import android.widget.EditText;

import com.example.plannr.models.UserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.views.ILoginView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    ILoginView mILoginView;

    public LoginPresenter(ILoginView view){
        mILoginView = view;
    }

    public void handleLogin(EditText inputEmail, EditText inputPassword, DatabaseConnection db,
                                   FirebaseAuth mAuth) {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        if(!authHelper.validateEmail(email)){
            mILoginView.setEmailError();
        }
        if(!authHelper.validatePassword(password)){
            mILoginView.setPasswordError();
        }
        if(authHelper.validateEmail(email) && authHelper.validatePassword(password)){
            mILoginView.showLoadingLogin();
            UserModel.login(inputEmail, inputPassword, db, mAuth, mILoginView);
        }
    }
}

