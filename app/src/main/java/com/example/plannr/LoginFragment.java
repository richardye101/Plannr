package com.example.plannr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.databinding.FragmentLoginBinding;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    EditText inputEmail, inputPassword;
    ProgressDialog progressDialog;

    DatabaseConnection db;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

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
            boolean canAuthenticate = auth.canAuthenticate(inputEmail, inputPassword, progressDialog);
            if(canAuthenticate){
                auth.login(inputEmail, inputPassword, progressDialog,
                        db, mAuth, LoginFragment.this);
            }
        });

        binding.registerButtonLoginPage.setOnClickListener(view12 -> {
//                login logic
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}