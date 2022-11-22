package com.example.plannr;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plannr.databinding.FragmentLoginBinding;
import com.example.plannr.models.User;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    EditText inputEmail, inputPassword;
    ProgressDialog progressDialog;

    DatabaseConnection db;
    HashMap users = new HashMap<>();

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canAuthenticate = auth.canAuthenticate(inputEmail, inputPassword, progressDialog,
                        db, mAuth);
                if(canAuthenticate){
                    String email = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                NavHostFragment.findNavController(LoginFragment.this)
                                        .navigate(R.id.action_loginFragment_to_FirstFragment);
                                Log.d("User uid:", mAuth.getUid());
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
//                                +task.getException()
                            }
                        }
                    });
                }
            }
        });

        binding.registerButtonLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                login logic
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }

    public void onUsersObtained(HashMap users) {
//        users.clear(); // To remove old Data
        this.users.putAll(users);
        Log.d("stored users", String.valueOf(users));
        // Continue your own logic...
    }

//    public User getUser(String username){
//        HashMap userMap = (HashMap) users.get(User.generateHash(username));
////        Log.d("theUserClass", String.valueOf(users.get(User.generateHash(username))));
//        User user = new User((String) userMap.get("username"), (String) userMap.get("password"));
//        if(user == null){
//            Log.e("user error", "No user found");
//            return null;
//        }
//        return user;
//    }

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //     * @param param1 Parameter 1.
     //     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static LoginFragment newInstance(String param1, String param2) {
//        LoginFragment fragment = new LoginFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
}