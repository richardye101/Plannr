package com.example.plannr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.databinding.FragmentDisplayCoursesBinding;
import com.example.plannr.course.Course;
import com.example.plannr.services.CourseRepository;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

public class DisplayCoursesFragment extends Fragment {
    private FragmentDisplayCoursesBinding binding;
    private DatabaseConnection db;
    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private static final int MAX_CLICK_DURATION = 1000;
    private static final int MAX_CLICK_DISTANCE = 15;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = DatabaseConnection.getInstance();
        Course[] courses = getData();

        View myView = inflater.inflate(R.layout.fragment_display_courses,
                container, false);
        binding = FragmentDisplayCoursesBinding.inflate(inflater, container, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                100.0f);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout page = new LinearLayout((getContext()));
        page.setOrientation(LinearLayout.VERTICAL);
        //page.setBackgroundColor(getResources().getColor(android.R.color.black));
        ScrollView scroll = new ScrollView(getContext());
        //scroll.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));

        Button addCourse = new Button(getContext());
        addCourse.setText("Add Course");
        addCourse.setTextSize(20);
        //addCourse.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seafoam));

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DisplayCoursesFragment.this)
                        .navigate(R.id.action_DisplayCoursesFragment_to_adminAddFragment);
            }
        });

        binding.getRoot().addView(addCourse, buttonParams);
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

            child.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            child.setBackground(ContextCompat.getDrawable(
                                    getContext(), R.drawable.course_layout_clicked));
                            child.setPadding(10, 10, 10, 10);

                            pressStartTime = System.currentTimeMillis();
                            pressedX = event.getX();
                            pressedY = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            child.setBackground(ContextCompat.getDrawable(
                                    getContext(), R.drawable.course_layout_border));
                            child.setPadding(10, 10, 10, 10);

                            long pressDuration = System.currentTimeMillis() - pressStartTime;
                            if (pressDuration < MAX_CLICK_DURATION && distance(pressedX, pressedY,
                                    event.getX(), event.getY()) < MAX_CLICK_DISTANCE) {
//                                NavHostFragment.findNavController(DisplayCoursesFragment.this)
//                                        .navigate(R.id.action_DisplayCoursesFragment_to_placeholder);
                            }
                            break;
                    }
                    return true;
                }
            });
        }

        return binding.getRoot();
    }

    private Course[] getData() {
        pullData();

        Course[] courses = new Course[CourseRepository.getCourses().size()];

        for(int i = 0; i< CourseRepository.getCourses().size(); i++) {
            courses[i] = CourseRepository.getCourses().get(i);
        }
        return courses;
    }

    public void pullData() {
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
                }
            }
        });
    }

    public void commitData(Map<String, Object> courses) {
        CourseRepository repository = CourseRepository.getInstance();

        for(Map.Entry<String, Object> entry : courses.entrySet()) {
            String name = ((Map) entry.getValue()).get("courseName").toString();
            String code = ((Map) entry.getValue()).get("courseCode").toString();
            String prerequisites = ((Map) entry.getValue()).get("prerequisites").toString();
            boolean fall = ((Map) entry.getValue()).get("fallAvailability").equals("true");
            boolean summer = ((Map) entry.getValue()).get("summerAvailability").equals("true");
            boolean winter = ((Map) entry.getValue()).get("winterAvailability").equals("true");

            Course temp = new Course(code, name, fall, summer, winter, prerequisites);

            repository.addCourse(temp);
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
}