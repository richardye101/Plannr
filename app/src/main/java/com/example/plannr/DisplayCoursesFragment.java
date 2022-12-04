package com.example.plannr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseRepository;
import com.example.plannr.databinding.FragmentDisplayCoursesBinding;
import com.example.plannr.services.DatabaseConnection;

import java.util.Map;

/**
 * Generate scrollable list of buttons representing each available course.
 * Each course button takes the user to the edit course page.
 */

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = DatabaseConnection.getInstance();

//        View myView = inflater.inflate(R.layout.fragment_display_courses,
//                container, false);
        binding = FragmentDisplayCoursesBinding.inflate(inflater, container, false);

        pullData();

        return binding.getRoot();
    }

    private Course[] getData() {
        Course[] courses = new Course[CourseRepository.getCourses().size()];

        for (int i = 0; i < CourseRepository.getCourses().size(); i++) {
            courses[i] = CourseRepository.getCourses().get(i);
        }
        return courses;
    }

    public void pullData() {
        db.ref.child("offerings").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                commitData((Map<String, Object>) task.getResult().getValue());
            }
        });
    }

    public void commitData(Map<String, Object> courses) {
        if(courses == null || courses.isEmpty())
            return;

        CourseRepository repository = CourseRepository.getInstance();

        for (Map.Entry<String, Object> entry : courses.entrySet()) {
            String name = ((Map) entry.getValue()).get("courseName").toString();
            String code = ((Map) entry.getValue()).get("courseCode").toString();
            String prerequisites = ((Map) entry.getValue()).get("prerequisites").toString();
            boolean fall = ((Map) entry.getValue()).get("fallAvailability").toString().equals("true");
            boolean summer = ((Map) entry.getValue()).get("summerAvailability").toString().equals("true");
            boolean winter = ((Map) entry.getValue()).get("winterAvailability").toString().equals("true");
            int id = Integer.parseInt(entry.getKey());

            Course temp = new Course(code, name, fall, summer, winter, prerequisites, id);

            repository.addCourse(temp);
        }

        generatePage();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void generatePage() {
        Course[] courses = getData();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f);

        LinearLayout page = new LinearLayout((getContext()));
        page.setOrientation(LinearLayout.VERTICAL);
        ScrollView scroll = new ScrollView(getContext());
        TextView title = new TextView(getContext());
        title.setText("Available Courses:");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(34);
//        android:layout_height="50dp"
//        android:layout_weight="1"
//        android:layout_width="match_parent"
//        android:text="Available Courses:"
//        android:textAlignment="center"
//        android:textSize="34sp" />

        Button refreshPage = new Button(getContext());
        refreshPage.setText("Refresh courses");
        refreshPage.setTextSize(20);

        refreshPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.getRoot().removeAllViewsInLayout(); //.removeAllViews();
                CourseRepository.removeAllCourses();
                pullData();
            }
        });

        binding.addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DisplayCoursesFragment.this)
                        .navigate(R.id.action_DisplayCoursesFragment_to_adminAddFragment);
            }
        });

        binding.getRoot().addView(title, buttonParams);
        binding.getRoot().addView(refreshPage, buttonParams);
        binding.getRoot().addView(addCourse, buttonParams);
        if(courses.length == 0){
            TextView noCourses = new TextView(getContext());
            noCourses.setText("There are no courses!");
            noCourses.setTextSize(20);
            binding.getRoot().addView(noCourses, buttonParams);
            scroll.addView(page, layoutParams);
            binding.getRoot().addView(scroll, layoutParams);
        }
        else{
            scroll.addView(page, layoutParams);
            binding.getRoot().addView(scroll, layoutParams);
            for (Course course : courses) {
                final String name = course.getCourseName();
                final String code = course.getCourseCode();
        binding.createCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(DisplayCoursesFragment.this)
//                        .navigate(R.id.action);
            }
        });

        scroll.addView(page, layoutParams);
        binding.fragmentDisplayCourses.addView(scroll, layoutParams);

        for (Course course : courses) {
            final String name = course.getCourseName();
            final String code = course.getCourseCode();
            final int id = course.getId();

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
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            child.setBackground(ContextCompat.getDrawable(
                                    getContext(), R.drawable.course_layout_border));
                            child.setPadding(10, 10, 10, 10);
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
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

                                //redirect to editing page
                                TextView text = (TextView) child.getChildAt(1);
                                String code = text.getText().toString();

                                CourseRepository.setSelectedCourseId(id);

//                                db.ref.child("selected").child("CourseCode").setValue(code);

                                NavHostFragment.findNavController(DisplayCoursesFragment.this)
                                        .navigate(R.id.action_DisplayCoursesFragment_to_adminEditFragment);
                            }
                            break;
                    }
                    return true;
                }
            });
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