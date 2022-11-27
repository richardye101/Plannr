package com.example.plannr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
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

// tests if a certain message is displayed in the view
    @Test
    public void testPresenterLogin(){
//        when(view.getEmail()).thenReturn("richard@email.com");
////        when(model.isFound("abc")).thenReturn(true);
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
    }


}

