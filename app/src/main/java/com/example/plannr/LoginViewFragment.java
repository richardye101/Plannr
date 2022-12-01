package com.example.plannr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.databinding.FragmentLoginViewBinding;
import com.example.plannr.models.Course;
import com.example.plannr.models.UserModel;
import com.example.plannr.services.CourseRepository;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.presenters.LoginPresenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

/**
 * A fragment representing the login view, and handles the operations required.
 * This implements the View in MVP
 */
public class LoginViewFragment extends Fragment implements Contract.ILoginView {

    private FragmentLoginViewBinding binding;

    EditText inputEmail, inputPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseConnection db;
    private Contract.ILoginPresenter presenter;

    public LoginViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        db = DatabaseConnection.getInstance();
        UserModel user = new UserModel();
        user.setAuth(mAuth);
        user.setDb(db);
        presenter = new LoginPresenter(LoginViewFragment.this, user);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEmail = binding.inputEmailAddress;
        inputPassword = binding.inputPassword;

        binding.loginButton.setOnClickListener(view1 -> {
            String email = getEmail();
            String password = getPassword();
            presenter.handleLogin(email, password);
        });

        binding.registerButtonLoginPage.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(LoginViewFragment.this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    public String getEmail(){
        String email = inputEmail.getText().toString();
        Log.d("entered login email", email);
        return email;
    }

    public String getPassword(){
        String password = inputPassword.getText().toString();
        return password;
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

    public void loginSuccess(String name){
//        NavHostFragment.findNavController(LoginViewFragment.this)
//                .navigate(R.id.action_loginFragment_to_FirstFragment);
        getData();

        Toast.makeText(getActivity(),
                "Login Successful, welcome " + name, Toast.LENGTH_SHORT).show();
    }

    public void loginFailure(){
        Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
    }

    public void loginUserNotFound(){
        Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
    }

    public void getData() {
        db.ref.child("offerings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    commitData((Map<String, Object>) task.getResult().getValue());

                    Toast.makeText(getActivity(),
                            "Database query successful", Toast.LENGTH_SHORT).show();

                    NavHostFragment.findNavController(LoginViewFragment.this)
                            .navigate(R.id.action_loginFragment_to_DisplayCoursesFragment);
                }
            }
        });
    }

    public void commitData(Map<String, Object> courses) {
        CourseRepository repository = CourseRepository.getInstance();

        for(Map.Entry<String, Object> entry : courses.entrySet()) {
            String name = ((Map) entry.getValue()).get("courseName").toString();
            String code = ((Map) entry.getValue()).get("courseCode").toString();
            String[] prerequisites = ((Map) entry.getValue()).get("prerequisites").toString().split(";");
            boolean fall = ((Map) entry.getValue()).get("fallAvailability").equals("true");
            boolean summer = ((Map) entry.getValue()).get("summerAvailability").equals("true");
            boolean winter = ((Map) entry.getValue()).get("winterAvailability").equals("true");
            String id = entry.getKey();

            Course temp = new Course(name, code, prerequisites, fall, summer, winter, id);
            System.out.println(temp.getName() + ", " + temp.getCode() + ", " + temp.getFallAvailablility());

            repository.addCourse(temp);
        }
    }
}