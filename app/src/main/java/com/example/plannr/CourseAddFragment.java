package com.example.plannr;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseRepository;
import com.example.plannr.databinding.FragmentCourseAddBinding;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.presenters.TableInputPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseAddFragment extends Fragment {

    private FragmentCourseAddBinding binding;

    EditText takenCoursesInput;
    TextView testView;
    Button addTakenCourseButton;
    TableInputPresenter presenter;

    public CourseAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new TableInputPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCourseAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        takenCoursesInput = binding.takenCoursesInput;
        addTakenCourseButton = binding.addTakenCourseButton;
        testView = binding.testView;

        addTakenCourseButton.setOnClickListener(view1 -> {

            List<String> alreadyTaken = StudentUserModel.getInstance().getTakenCoursesList();
            Log.d("Taken courses", alreadyTaken.toString());
            List<Course> allCourses = CourseRepository.getCourses();

//                convert list of courses into hashmap of course code and id
            Map<String, Integer> courseIds = new HashMap<>();
            for(Course course : allCourses){
                courseIds.put(course.getCourseCode(),course.getId());
            }

            ArrayList<String> courseCodes = Course.stringToArraylist(takenCoursesInput.getText().toString());
//                pull out every id of the courses the student has taken
            for(String code : courseCodes) {
                if(courseIds.containsKey(code)) {
                    int courseId = courseIds.get(code);
                    alreadyTaken.add(String.valueOf(courseId));
                    Toast.makeText(getActivity(), "Course: "+ code + " successfully added", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_courseAddFragment_to_displayTakenCoursesFragment);
                   }
                else {
                    Toast.makeText(getActivity(), "Course: "+ code + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            StudentUserModel user = StudentUserModel.getInstance();
            user.setTakenCourses(TextUtils.join(";", alreadyTaken));

            DatabaseConnection db = DatabaseConnection.getInstance();
            db.ref.child("users").child(user.getId()).child("taken").setValue(user.getTakenCourses());
        });
    }

    public String getTakenCoursesInput() {
        return takenCoursesInput.getText().toString();
    }

    public EditText getTakenCoursesInputView() {
        return takenCoursesInput;
    }

    public TextView getTest() {
        return testView;
    }
}