package com.example.plannr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plannr.databinding.FragmentLoginViewBinding;
import com.example.plannr.databinding.FragmentStudentCourseListBinding;
import com.example.plannr.models.AdminUserModel;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.authHelper;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentCourseList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentCourseList extends Fragment {

    private FragmentStudentCourseListBinding binding;
    private DatabaseConnection db;
    private StudentUserModel student;

    public StudentCourseList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentCourseList.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentCourseList newInstance(String param1, String param2) {
        StudentCourseList fragment = new StudentCourseList();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseConnection.getInstance();
        student = StudentUserModel.getInstance();

        //Get courses from student user model
        //Call database, get courses based on course codes
        //Set courses to textviews

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentCourseListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.button1.setOnClickListener(view1 -> {
            db.ref.child("offerings").child(uid).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Add new courses
                    //Upload to database, display success message
                }
            });
        });

        binding.registerButtonLoginPage.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(LoginViewFragment.this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}