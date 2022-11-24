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

import com.example.plannr.databinding.FragmentRegisterBinding;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.authPresenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    EditText inputEmail, inputName, inputPassword, inputConfirmPassword;
    ProgressDialog progressDialog;

    DatabaseConnection db;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        db = DatabaseConnection.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEmail = binding.inputEmailRegister;
        inputName = binding.inputName;
        inputPassword = binding.inputPasswordRegister;
        inputConfirmPassword = binding.inputConfirmPasswordRegister;

        binding.registerButtonRegisterPage.setOnClickListener(viewArg -> {
            authPresenter.handleRegistration(inputEmail, inputName, inputPassword,
                    inputConfirmPassword, progressDialog, db,
                    mAuth, RegisterFragment.this);
        });

        binding.loginButtonRegisterPage.setOnClickListener(viewArg -> {
            NavHostFragment.findNavController(RegisterFragment.this)
                    .navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }
}