package com.example.plannr.admin.adminAdd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plannr.R;
import com.example.plannr.databinding.FragmentAdminAddBinding;
import com.example.plannr.databinding.FragmentLoginViewBinding;
import com.example.plannr.services.DatabaseConnection;

/**
 * AdminAdd fragment class responsible for getting the reference to the different view components
 */

public class AdminAddFragment extends Fragment {

    private FragmentAdminAddBinding binding;
    AdminAddPresenter presenter;

    public AdminAddFragment() {
//        presenter = new AdminAddPresenter(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AdminAddPresenter(AdminAddFragment.this);

        binding.adminAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addCourse();
            }
        });

    }

    public String getCourseName(){
        EditText coursename = getView().findViewById(R.id.adminAddCourseNameField);
        return coursename.getText().toString();
    }

    public String getCourseCode(){
        EditText coursecode = getView().findViewById(R.id.adminAddCourseCodeField);
        return coursecode.getText().toString();
    }

    public boolean getFallAvailability(){
        CheckBox fall = getView().findViewById(R.id.fall);
        return fall.isChecked();
    }
    public boolean getWinterAvailability(){
        CheckBox winter = getView().findViewById(R.id.winter);
        return winter.isChecked();
    }

    public boolean getSummerAvailability(){
        CheckBox summer = getView().findViewById(R.id.summer);
        return summer.isChecked();
    }

    public String getPrerequisite(){
        EditText prerequisite = getView().findViewById(R.id.adminAddPrerequisiteField);
        return prerequisite.getText().toString();
    }

    public TextView getWarningText(){
        TextView warning = getView().findViewById(R.id.warningText);
        return warning;
    }

}