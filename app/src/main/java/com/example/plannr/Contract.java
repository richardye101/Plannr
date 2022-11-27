package com.example.plannr;

import com.example.plannr.models.UserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.auth.FirebaseAuth;

public interface Contract {
    /**
     * An interface representing the user model, and specifies the operations required.
     * This is model in MVP
     */
    public interface IUserModel {
        public void setEmail(String email);
        public void setName(String name);
        public void setIsAdmin(boolean admin);
        public String getEmail();
        public String getName();
        public boolean getIsAdmin();
        public UserModel getUser();
        public void setUser(UserModel user);

        void createUserInDb(DatabaseConnection db, String id, String email, String name);
        void register(String email, String name, String password, String confirmPassword,
                      DatabaseConnection db, FirebaseAuth mAuth, Contract.IRegisterView rf);
        void login(String email, String password,
                   DatabaseConnection db, FirebaseAuth mAuth, ILoginView lf);
        void setUserLocallyAndUpdateView(String uid, DatabaseConnection db, Contract.ILoginView lf);
    }

    /**
     * An interface representing the login view, and specifies the operations required.
     * This implements the View in MVP
     */
    public interface ILoginView {
        void showLoadingLogin();
        void hideLoadingLogin();
        void setEmailError();
        void setPasswordError();
        void loginSuccess(Contract.IUserModel user);
        void loginFailure();
    }

    public interface IRegisterView {
        void showLoadingRegister();
        void hideLoadingRegister();
        void setEmailError();
        void setPasswordError();
        void setConfirmPasswordError();
        void registerSuccess();
        void registerFailure();
    }

    public interface ILoginPresenter{
        public void handleLogin(String email, String password, DatabaseConnection db,
                                FirebaseAuth mAuth);
    }

    /**
     * An interface representing the register view, and handles the operations required.
     * This implements the View in MVP
     */
    public interface IRegisterPresenter{
        public void handleRegistration(String email, String name, String password,
                                       String confirmPassword,DatabaseConnection db,
                                       FirebaseAuth mAuth);
    }
}
