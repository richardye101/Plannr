package com.example.plannr.students;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.R;
import com.example.plannr.course.Course;
import com.example.plannr.course.CourseRepository;
import com.example.plannr.course.TakenCourseRepository;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

/**
 * Generate scrollable list of buttons representing each taken course.
 */

public class DisplayTakenCoursesFragment extends Fragment {
    private com.example.plannr.databinding.FragmentDisplayTakenCoursesBinding binding;
    private DatabaseConnection db;
    private StudentUserModel user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = DatabaseConnection.getInstance();
        user = StudentUserModel.getInstance();

        binding = com.example.plannr.databinding.FragmentDisplayTakenCoursesBinding.inflate(inflater, container, false);

        pullData();

        return binding.getRoot();
    }

    private Course[] getData() {
        Course[] courses = new Course[TakenCourseRepository.getCourses().size()];

        for (int i = 0; i < TakenCourseRepository.getCourses().size(); i++) {
            courses[i] = TakenCourseRepository.getCourses().get(i);
        }
        return courses;
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
//        TakenCourseRepository takenRepository = TakenCourseRepository.getInstance();
//        CourseRepository repository = CourseRepository.getInstance();
        List<String> takenCourses = user.getTakenCoursesList();
        Log.d("db taken courses", takenCourses.toString());
//        if(!takenCourses.isEmpty()){
        for (Map.Entry<String, Object> entry : courses.entrySet()) {
            int id = Integer.parseInt(entry.getKey());
            String name = ((Map) entry.getValue()).get("courseName").toString();
            String code = ((Map) entry.getValue()).get("courseCode").toString();
            String prerequisites = ((Map) entry.getValue()).get("prerequisites").toString();
            boolean fall = ((Map) entry.getValue()).get("fallAvailability").equals("true");
            boolean summer = ((Map) entry.getValue()).get("summerAvailability").equals("true");
            boolean winter = ((Map) entry.getValue()).get("winterAvailability").equals("true");
            Course temp = new Course(code, name, fall, summer, winter, prerequisites, id);
            if(takenCourses.contains(String.valueOf(id))){
                TakenCourseRepository.addCourse(temp);
            }
            CourseRepository.addCourse(temp);
        }
        Log.d("generated taken courses", TakenCourseRepository.getCourses().toString());
        generatePage();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void generatePage() {
        Course[] courses = getData();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout page = new LinearLayout((getContext()));
        page.setOrientation(LinearLayout.VERTICAL);
        ScrollView scroll = new ScrollView(getContext());

        scroll.addView(page, layoutParams);
        binding.fragmentDisplayTakenCourses.addView(scroll, layoutParams);

        Button tableMakerButton = binding.createTimetableButton;
        Button addCourse = binding.addTakenCourseButton;

        tableMakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DisplayTakenCoursesFragment.this)
                        .navigate(R.id.action_displayTakenCoursesFragment_to_tableInputFragment);
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DisplayTakenCoursesFragment.this)
                        .navigate(R.id.action_displayTakenCoursesFragment_to_courseAddFragment);
            }
        });

        if(courses.length == 0){
            TextView noCourses = new TextView(getContext());
            noCourses.setText("You have not taken any courses!");
            noCourses.setTextSize(20);

            page.addView(noCourses);
        }
        else{
            scroll.addView(page, layoutParams);
            binding.getRoot().addView(scroll, layoutParams);

            for (Course course : courses) {
                final String name = course.getCourseName();
                final String code = course.getCourseCode();

                LinearLayout child = new LinearLayout(getContext());

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);

                child.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_layout_border));
                child.setOrientation(LinearLayout.VERTICAL);
                child.setPadding(10, 10, 10, 10);
                child.setGravity(Gravity.TOP);

                page.addView(child, params);

                child.addView(createText(name, 20));
                child.addView(createText(code, 15));
            }
        }
    }

    private TextView createText(String text, int fontSize) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(fontSize);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        return textView;
    }

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        }
        else
        {
            pullData();
        }
    }
}