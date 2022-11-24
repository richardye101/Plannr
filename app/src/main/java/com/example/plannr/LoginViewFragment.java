package com.example.plannr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.databinding.FragmentLoginViewBinding;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.LoginPresenter;

import com.example.plannr.views.ILoginView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A fragment representing the login view, and handles the operations required.
 * This implements the View in MVP
 */
public class LoginViewFragment extends Fragment implements ILoginView {

    private FragmentLoginViewBinding binding;

    EditText inputEmail, inputPassword;
    ProgressDialog progressDialog;

    DatabaseConnection db;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    LoginPresenter presenter;

    public LoginViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
        binding = FragmentLoginViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        presenter = new LoginPresenter(LoginViewFragment.this);

        db = DatabaseConnection.getInstance();
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEmail = binding.inputEmailAddress;
        inputPassword = binding.inputPassword;

        binding.loginButton.setOnClickListener(view1 -> {
            presenter.handleLogin(inputEmail, inputPassword, db, mAuth);
        });

        binding.registerButtonLoginPage.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(LoginViewFragment.this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    public void setEmailError(){
        inputEmail.setError("Invalid Email");
    }

    public void setPasswordError(){
        inputPassword.setError("Password needs 6 or more characters");
    }

    public void showLoadingLogin(){
        progressDialog.setMessage("Logging in...");
        progressDialog.setTitle("Login");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public void hideLoadingLogin(){
        progressDialog.dismiss();
    }
    public void loginSuccess(){
        NavHostFragment.findNavController(LoginViewFragment.this)
                .navigate(R.id.action_loginFragment_to_FirstFragment);
        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
    }

    public void loginFailure(){
        Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
    }
}