package com.example.plannr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.plannr.models.UserModel;
import com.example.plannr.presenters.LoginPresenter;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import static org.mockito.asm.util.CheckClassAdapter.verify;


/**
 * Local unit test for LoginPresenter, which will execute on the development machine (host).
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterUnitTest {

    @Mock
    Contract.ILoginView view;

    @Mock
    Contract.IUserModel model;

    FirebaseAuth mAuth;

    DatabaseConnection db;

    Contract.ILoginPresenter presenter;

    @Before
    public void before() {
        presenter = new LoginPresenter(view, model);
    }

//    test login email error
    @Test
    public void testLoginEmailError(){
        presenter.handleLogin("wrongemail.com", "12345g");
        verify(view).setEmailError();
        verify(view, never()).setPasswordError();
        verify(view, never()).showLoadingLogin();
        verify(model, never()).login(anyString(), anyString());
    }
//    test login password error
    @Test
    public void testLoginPasswordError(){
        presenter.handleLogin("wrong@email.com", "123");
        verify(view).setPasswordError();
        verify(view, never()).setEmailError();
        verify(view, never()).showLoadingLogin();
        verify(model, never()).login(anyString(), anyString());
    }

//    test login success for admin
//      (hideLoadingLogin, showLoadingLogin, UserModel.login, setUserLocallyAndUpdateView)
    @Test
    public void testLogin(){
        presenter.handleLogin("admin@test.com", "123456");
        verify(view, never()).setEmailError();
        verify(view, never()).setPasswordError();
        InOrder order = inOrder(model, view);
        order.verify(view).showLoadingLogin();
        order.verify(model).login(anyString(), anyString());
    }

// tests written to test further but will not use as requirements only indicate LoginPresenter needs testing
////    test login failure for admin
////      (showLoadingLogin and loginFailure)
//    @Test
//    public void testLoginFailureAdmin(){
////        when(view.getEmail()).thenReturn("admin@test.com");
////        when(view.getPassword()).thenReturn("12345");
//
//        presenter.handleLogin("admin@test.com", "12345");
//        verify(view, never()).setEmailError();
//        verify(view, never()).setPasswordError();
//        InOrder order = inOrder(model, view);
//        order.verify(view).showLoadingLogin();
//        order.verify(model).login(anyString(), anyString());
////        order.verify(view).hideLoadingLogin();
////        order.verify(view).loginFailure();
//    }
////    test login success for student
////      (hideLoadingLogin, showLoadingLogin, UserModel.login, setUserLocallyAndUpdateView)
//    @Test
//    public void testLoginSuccessStudent(){
//        presenter.handleLogin("student@email.com", "12345g");
//        verify(view, never()).setEmailError();
//        verify(view, never()).setPasswordError();
//        InOrder order = inOrder(model, view);
//        order.verify(view).showLoadingLogin();
//        order.verify(model).login(anyString(), anyString());
//        order.verify(view).hideLoadingLogin();
//        order.verify(model).setUserLocallyAndUpdateView(anyString());
//        order.verify(model).createLoggedInUser(anyString(), anyString(), false);
//        order.verify(view).loginSuccess("John Doe");
//    }
////    test login failure for student
////      (showLoadingLogin and loginFailure)
//    @Test
//    public void testLoginFailureStudent(){
//        presenter.handleLogin("student@email.com", "12345");
//        verify(view, never()).setEmailError();
//        verify(view, never()).setPasswordError();
//        InOrder order = inOrder(model, view);
//        order.verify(view).showLoadingLogin();
//        order.verify(model).login(anyString(), anyString());
//        order.verify(view).hideLoadingLogin();
//        order.verify(view).loginFailure();
//    }
////    test login failure for unregistered user
////      (showLoadingLogin and loginFailure)
//    @Test
//    public void testLoginFailureUnregisteredUser(){
////        when(view.getEmail()).thenReturn("fakestudent@email.com");
////        when(view.getPassword()).thenReturn("12345");
//
//        presenter.handleLogin("fakestudent@email.com", "12345");
//        verify(view, never()).setEmailError();
//        verify(view, never()).setPasswordError();
//        InOrder order = inOrder(model, view);
//        order.verify(view).showLoadingLogin();
//        order.verify(model).login(anyString(), anyString());
//        order.verify(view).hideLoadingLogin();
//        order.verify(view).loginUserNotFound();
//    }


// code from lecture, save for later reference
//    @Test
//    public void testPresenterLogin(){
//        when(view.getEmail()).thenReturn("richard@email.com");
//        when(model.isFound("abc")).thenReturn(true);
//        LoginPresenter presenter = new Contract.ILoginPresenter(view, model);
//
////        verify(view).displayMessage("user found");
//
////        check how many times displayMessage is invoked
//        verify(view, times(2)).displayMessage("user found");
//
////        want to know if the arguments are what we expect
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
////        this doesnt actually make sense, but if we passed a string into getPassword then it would
//        verify(view).getPassword(captor.capture());
//        assertEquals(captor.getValue(), "user found");
//
////        check if a method is invoked regardless of the argument passed in
//        verify(view).displayMessage(anyString());
//
////        check if specific order of method are called correctly
//        InOrder order = inOrder(model, view);
//        order.verify(model).isFound("first method call");
//        order.verify(view).displayMessage("second method call");
//    }

}

