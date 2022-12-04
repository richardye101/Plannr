package com.example.plannr.admin.adminEdit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plannr.R;
import com.example.plannr.course.Course;
import com.example.plannr.course.CourseRepository;
import com.example.plannr.databinding.FragmentAdminEditBinding;

/**
 * AdminEdit fragment class responsible for getting the reference to the components of the view
 */

public class AdminEditFragment extends Fragment {

    private FragmentAdminEditBinding binding;
    private AdminEditPresenter presenter;

    public AdminEditFragment() {
        presenter = new AdminEditPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.adminEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.editCourse();
            }
        });

        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.removeCourse();
            }
        });

        EditText courseName = binding.adminEditNameField;
        EditText courseCode = binding.adminEditCodeField;
        CheckBox fall = binding.editFall;
        CheckBox summer = binding.editSummer;
        CheckBox winter = binding.editWinter;
        EditText prereqs = binding.adminEditPrerequisiteField;

        Course selected = CourseRepository.getSelectedCourse();

        courseName.setText(selected.getCourseName());
        courseCode.setText(selected.getCourseCode());
        fall.setChecked(selected.getFallAvailability());
        summer.setChecked(selected.getSummerAvailability());
        winter.setChecked(selected.getWinterAvailability());

        String temp = "";

        if(!selected.getPrerequisites().isEmpty()) {
            String[] prerequisites = selected.getPrerequisites().split(",");

            for (int i=0; i<prerequisites.length; i++) {
                if(!prerequisites[i].isEmpty()) {
                    if(i > 0  && !prerequisites[i-1].isEmpty())
                        temp += ",";
                    temp += CourseRepository.getCourseById(Integer.parseInt(prerequisites[i])).getCourseCode();
                }
            }
        }

        prereqs.setText(temp);
    }

    public String getEditCourseName() {
        EditText coursename = getView().findViewById(R.id.adminEditNameField);
        return coursename.getText().toString();
    }

    public String getEditCourseCode() {
        EditText coursecode = getView().findViewById(R.id.adminEditCodeField);
        return coursecode.getText().toString();
    }

    public boolean getEditFallAvailability() {
        CheckBox fall = getView().findViewById(R.id.editFall);
        return fall.isChecked();
    }

    public boolean getEditWinterAvailability() {
        CheckBox winter = getView().findViewById(R.id.editWinter);
        return winter.isChecked();
    }

    public boolean getEditSummerAvailability() {
        CheckBox summer = getView().findViewById(R.id.editSummer);
        return summer.isChecked();
    }

    public String getEditPrerequisite() {
        EditText prerequisite = getView().findViewById(R.id.adminEditPrerequisiteField);
        return prerequisite.getText().toString();
    }

    public TextView getEditWarningText() {
        TextView warning = getView().findViewById(R.id.editWarningText);
        return warning;
    }
}
