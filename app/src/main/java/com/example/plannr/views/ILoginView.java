package com.example.plannr.views;

/**
 * An interface representing the login view, and handles the operations required.
 * This implements the View in MVP
 */
public interface ILoginView {
    void showLoadingLogin();
    void hideLoadingLogin();
    void setEmailError();
    void setPasswordError();
    void loginSuccess();
    void loginFailure();
}
