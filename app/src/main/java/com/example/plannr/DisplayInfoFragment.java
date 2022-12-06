package com.example.plannr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plannr.course.Course;
import com.example.plannr.course.TakenCourseRepository;
import com.example.plannr.databinding.FragmentDisplayInfoBinding;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public class DisplayInfoFragment extends Fragment {
    private DatabaseConnection db;
    private FragmentDisplayInfoBinding binding;
    private StudentUserModel user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDisplayInfoBinding.inflate(inflater, container, false);
        db = DatabaseConnection.getInstance();
        user = StudentUserModel.getInstance();

        pullData();

        return binding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            pullData();
        }
    }

    public void pullData() {
        db.ref.child("offerings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    loadTakenCourses((Map<String, Object>) task.getResult().getValue());
                }
            }
        });
    }

    public void loadTakenCourses(Map<String, Object> courses) {
        List<String> takenCourses = user.getTakenCoursesList();
        Log.d("db taken courses", takenCourses.toString());

        for (Map.Entry<String, Object> entry : courses.entrySet()) {
            String name = ((Map) entry.getValue()).get("courseName").toString();
            String code = ((Map) entry.getValue()).get("courseCode").toString();
            String prerequisites = ((Map) entry.getValue()).get("prerequisites").toString();
            boolean fall = ((Map) entry.getValue()).get("fallAvailability").toString().equals("true");
            boolean summer = ((Map) entry.getValue()).get("summerAvailability").toString().equals("true");
            boolean winter = ((Map) entry.getValue()).get("winterAvailability").toString().equals("true");
            int id = Integer.parseInt(entry.getKey());

            Course temp = new Course(code, name, fall, summer, winter, prerequisites, id);

            if(takenCourses.contains(String.valueOf(id))) {
                TakenCourseRepository.addCourse(temp);
            }
        }
        Log.d("generated taken courses", TakenCourseRepository.getCourses().toString());
        update();
    }

    private void update() {
        EditText courseName = binding.adminEditNameField;
        EditText courseCode = binding.adminEditCodeField;
        CheckBox fall = binding.editFall;
        CheckBox summer = binding.editSummer;
        CheckBox winter = binding.editWinter;
        EditText prereqs = binding.adminEditPrerequisiteField;

        Course selected = TakenCourseRepository.getSelectedCourse();

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
                    Course prereq = TakenCourseRepository.getCourseById(Integer.parseInt(prerequisites[i]));
                    if(prereq != null)
                        temp += prereq.getCourseCode();
                }
            }
        }

        prereqs.setText(temp);
    }
}