package com.example.plannr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.plannr.databinding.FragmentDisplayCoursesBinding;
import com.example.plannr.databinding.FragmentViewCoursesBinding;
import com.example.plannr.models.Course;
import com.example.plannr.services.CourseRepository;

import java.util.Map;

/**
 * Available courses are read from CourseRepository
 * This fragment constructs a list of buttons, each showing an available course
 * Each button takes the user to more options for its specified course
 */

public class DisplayCoursesFragment extends Fragment {
    private FragmentDisplayCoursesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDisplayCoursesBinding.inflate(inflater, container, false);

        View myView = inflater.inflate(R.layout.fragment_display_courses, container, false);
        LinearLayout linearlayout = new LinearLayout(getActivity());

        CourseRepository repository = CourseRepository.getInstance();
        Course[] courses = getData(repository);

        for (int i=0; i<courses.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            int id = courses[i].getName().hashCode();

            Button btn = new Button(myView.getContext());
            btn.setId(id);

            btn.setText(courses[i].getName());
            btn.setTextSize(20);

            btn.setBackgroundColor(Color.CYAN);
            btn.setLayoutParams(params);

            linearlayout.addView(btn, params);
            btn = myView.findViewById(id);

            btn.setVisibility(View.VISIBLE);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        return binding.getRoot();
    }

    public Course[] getData(CourseRepository repository) {
        Course[] courses = new Course[repository.getCourses().size()];

        for(int i=0; i<repository.getCourses().size(); i++) {
            courses[i] = repository.getCourses().get(i);
        }
        return courses;
    }
}
