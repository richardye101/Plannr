package com.example.plannr;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plannr.databinding.FragmentRegisterViewBinding;
import com.example.plannr.models.UserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.presenters.RegisterPresenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterViewFragment extends Fragment implements Contract.IRegisterView {

    private FragmentRegisterViewBinding binding;

    EditText inputEmail, inputName, inputPassword, inputConfirmPassword;
    ProgressDialog progressDialog;

    DatabaseConnection db;

    Contract.IRegisterPresenter presenter;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public RegisterViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        presenter = new RegisterPresenter(RegisterViewFragment.this, new UserModel());

        db = DatabaseConnection.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
        binding = FragmentRegisterViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEmail = binding.inputEmailRegister;
        inputName = binding.inputName;
        inputPassword = binding.inputPasswordRegister;
        inputConfirmPassword = binding.inputConfirmPasswordRegister;

        binding.registerButtonRegisterPage.setOnClickListener(viewArg -> {
            String email = inputEmail.getText().toString();
            String name = inputName.getText().toString();
            String password = inputPassword.getText().toString();
            String confirmPassword = inputConfirmPassword.getText().toString();
            presenter.handleRegistration(email, name, password,
                    confirmPassword, db, mAuth);
        });

        binding.loginButtonRegisterPage.setOnClickListener(viewArg -> {
            NavHostFragment.findNavController(RegisterViewFragment.this)
                    .navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    public void setEmailError() {
        inputEmail.setError("Invalid Email");
    }

    public void setPasswordError() {
        inputPassword.setError("Password needs 6 or more characters");
    }

    public void setConfirmPasswordError() {
        inputConfirmPassword.setError("Passwords do not match");
    }

    public void showLoadingRegister() {
        progressDialog.setMessage("Registering...");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoadingRegister() {
        progressDialog.dismiss();
    }

    public void registerSuccess() {
        NavHostFragment.findNavController(RegisterViewFragment.this)
                .navigate(R.id.action_registerFragment_to_loginFragment);
        Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
    }

    public void registerFailure() {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
//                                +task.getException()
    }
}