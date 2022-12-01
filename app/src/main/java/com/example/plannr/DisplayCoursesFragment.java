package com.example.plannr;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.plannr.databinding.FragmentDisplayCoursesBinding;
import com.example.plannr.models.Course;
import com.example.plannr.services.CourseRepository;

import org.w3c.dom.Text;

import java.util.Map;

public class DisplayCoursesFragment extends Fragment {
    private FragmentDisplayCoursesBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_display_courses,
                container, false);
        binding = FragmentDisplayCoursesBinding.inflate(inflater, container, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                100.0f);

        LinearLayout page = new LinearLayout((getContext()));
        page.setOrientation(LinearLayout.VERTICAL);
        //page.setBackgroundColor(getResources().getColor(android.R.color.black));
        ScrollView scroll = new ScrollView(getContext());
        //scroll.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));

        scroll.addView(page, layoutParams);
        binding.getRoot().addView(scroll, layoutParams);

        Course[] courses = getData();

        for(Course course : courses) {
            final String name = course.getName();
            final String code = course.getCode();

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

            System.out.println(name + ", " + code);

            child.addView(createText(name, 20));
            child.addView(createText(code, 15));

            child.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            child.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_layout_clicked));
                            child.setPadding(10, 10, 10, 10);
                            break;
                        case MotionEvent.ACTION_UP:
                            child.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_layout_border));
                            child.setPadding(10, 10, 10, 10);
                            break;
                    }
                    return true;
                }
            });

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(name);
                }
            });
        }

        return binding.getRoot();
    }

    public Course[] getData() {
        Course[] courses = new Course[CourseRepository.getCourses().size()];

        for(int i = 0; i< CourseRepository.getCourses().size(); i++) {
            courses[i] = CourseRepository.getCourses().get(i);
        }
        return courses;
    }

    public TextView createText(String text, int fontSize) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(fontSize);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        return textView;
    }
}