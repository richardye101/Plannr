package com.example.plannr.util;

import android.widget.EditText;

import com.example.plannr.models.UserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.views.IRegisterView;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter {

    IRegisterView mIRegisterView;

    public RegisterPresenter(IRegisterView view){
        mIRegisterView = view;
    }

    public void handleRegistration(EditText inputEmail, EditText inputName, EditText inputPassword,
                                   EditText inputConfirmPassword,DatabaseConnection db,
                                          FirebaseAuth mAuth) {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();
        if(!authHelper.validateEmail(email)){
            mIRegisterView.setEmailError();
        }
        else if(!authHelper.validatePassword(password)){
            mIRegisterView.setPasswordError();
        }
        else if(!authHelper.matchPasswordRegister(password, confirmPassword)){
            mIRegisterView.setConfirmPasswordError();
        }
        else if(authHelper.validateEmail(email) &&
                authHelper.validatePassword(password) &&
                authHelper.matchPasswordRegister(password, confirmPassword)){
            mIRegisterView.showLoadingRegister();
            UserModel.register(inputEmail, inputName, inputPassword, db,
                    mAuth, mIRegisterView);
        }
    }
}
