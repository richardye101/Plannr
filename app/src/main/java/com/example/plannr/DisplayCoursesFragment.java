package com.example.plannr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.plannr.databinding.FragmentDisplayCoursesBinding;
import com.example.plannr.models.Course;
import com.example.plannr.services.CourseRepository;

import java.util.Map;

public class DisplayCoursesFragment extends Fragment {
    private FragmentDisplayCoursesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_display_courses,
                container, false);
        binding = FragmentDisplayCoursesBinding.inflate(inflater, container, false);

        LinearLayout layout = (LinearLayout) myView.findViewById(R.id.fragment_display_courses);
        layout.setOrientation(LinearLayout.VERTICAL);

        CourseRepository repository = CourseRepository.getInstance();
        Course[] courses = getData(repository);

        for (int i=0; i<courses.length; i++) {
            final String name = courses[i].getName();
            final String code = courses[i].getCode();

            final Button button = new Button(getActivity());
            button.setId(code.hashCode());
            button.setBackgroundColor(getResources().getColor(R.color.white));
            button.setText(name + "\n" + code);
            button.setTextSize(20);

            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(0, 0, 0, 10);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(code);
                }
            });

            layout.addView(button, buttonParams);
        }
        binding.getRoot().addView(layout);

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
