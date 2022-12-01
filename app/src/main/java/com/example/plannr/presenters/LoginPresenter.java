package com.example.plannr.presenters;

import com.example.plannr.Contract;
import com.example.plannr.models.UserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.authHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements Contract.ILoginPresenter{
    private Contract.ILoginView mILoginView;
    private Contract.IUserModel mUserModel;

    public LoginPresenter(Contract.ILoginView view, Contract.IUserModel userModel){
        mILoginView = view;
        mUserModel = userModel;
        mUserModel.loginUserSetup(mILoginView);
    }

    public void handleLogin(String email, String password) {
        if(!(authHelper.validateEmail(email))){
            mILoginView.setEmailError();
        }
        if(!(authHelper.validatePassword(password))){
            mILoginView.setPasswordError();
        }
        if(authHelper.validateEmail(email) && authHelper.validatePassword(password)){
            mILoginView.showLoadingLogin();
            mUserModel.login(email, password);
        }
    }
}
