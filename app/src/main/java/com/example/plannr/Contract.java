package com.example.plannr;

import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.auth.FirebaseAuth;

public interface Contract {
    /**
     * An interface representing the user model, and specifies the operations required.
     * This is model in MVP
     */
    public interface IUserModel {
        public void loginUserSetup(Contract.ILoginView lf);
        public void registerUserSetup(Contract.IRegisterView rf);
        public void setAuth(FirebaseAuth mAuth);
        public void setDb(DatabaseConnection db);
        public void setEmail(String email);
        public void setName(String name);
        public void setIsAdmin(boolean admin);
        public String getEmail();
        public String getName();
        public boolean getIsAdmin();
        public IUserModel returnCurUser();
        public void createLoggedInUser(String email, String name, boolean isAdmin);

        void createUserInDb(String id, String email, String name);
        void register(String email, String name, String password, String confirmPassword);
        void login(String email, String password);
        void setUserLocallyAndUpdateView(String uid);
    }

    /**
     * An interface representing the login view, and specifies the operations required.
     * This implements the View in MVP
     */
    public interface ILoginView {
        public String getEmail();
        public String getPassword();
        void setEmailError();
        void setPasswordError();
        void showLoadingLogin();
        void hideLoadingLogin();
        void loginSuccess(String name);
        void loginFailure();
        void loginUserNotFound();
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
        public void handleLogin(String email, String password);
    }

    /**
     * An interface representing the register view, and handles the operations required.
     * This implements the View in MVP
     */
    public interface IRegisterPresenter{
        public void handleRegistration(String email, String name, String password,
                                       String confirmPassword);
    }
}
