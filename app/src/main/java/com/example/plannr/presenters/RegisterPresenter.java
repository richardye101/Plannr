package com.example.plannr.presenters;

import android.widget.EditText;

import com.example.plannr.Contract;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.authHelper;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter implements Contract.IRegisterPresenter{

    private Contract.IRegisterView mIRegisterView;
    private Contract.IUserModel mUserModel;

    public RegisterPresenter(Contract.IRegisterView view, Contract.IUserModel userModel){
        mIRegisterView = view;
        mUserModel = userModel;
    }

    public void handleRegistration(String email, String name, String password,
                                   String confirmPassword,DatabaseConnection db,
                                          FirebaseAuth mAuth) {
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
            mUserModel.register(email, name, password, confirmPassword, db,
                    mAuth, mIRegisterView);
        }
    }
}
