package com.example.plannr.views;

/**
 * An interface representing the register view, and handles the operations required.
 * This implements the View in MVP
 */
public interface IRegisterView {
    void showLoadingRegister();
    void hideLoadingRegister();
    void setEmailError();
    void setPasswordError();
    void setConfirmPasswordError();
    void registerSuccess();
    void registerFailure();
}
