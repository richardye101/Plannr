package com.example.plannr.admin.adminEdit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plannr.R;
import com.example.plannr.databinding.FragmentAdminAddBinding;
import com.example.plannr.databinding.FragmentAdminEditBinding;

/**
 * AdminEdit fragment class responsible for getting the reference to the components of the view
 */

public class AdminEditFragment extends Fragment {

    private FragmentAdminEditBinding binding;
    private AdminEditPresenter presenter2;

    public AdminEditFragment() {
        presenter2 = new AdminEditPresenter(this);
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
                presenter2.editCourse();
            }
        });

        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter2.removeCourse();
            }
        });
    }

    public String getEditCourseName(){
        EditText coursename = getView().findViewById(R.id.adminEditNameField);
        return coursename.getText().toString();
    }

    public String getEditCourseCode(){
        EditText coursecode = getView().findViewById(R.id.adminEditCodeField);
        return coursecode.getText().toString();
    }

    public boolean getEditFallAvailability(){
        CheckBox fall = getView().findViewById(R.id.editFall);
        return fall.isChecked();
    }
    public boolean getEditWinterAvailability(){
        CheckBox winter = getView().findViewById(R.id.editWinter);
        return winter.isChecked();
    }

    public boolean getEditSummerAvailability(){
        CheckBox summer = getView().findViewById(R.id.editSummer);
        return summer.isChecked();
    }

    public String getEditPrerequisite(){
        EditText prerequisite = getView().findViewById(R.id.adminEditPrerequisiteField);
        return prerequisite.getText().toString();
    }

    public TextView getEditWarningText(){
        TextView warning = getView().findViewById(R.id.editWarningText);
        return warning;
    }
}