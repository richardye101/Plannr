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

        RelativeLayout layout = (RelativeLayout) myView.findViewById(R.id.fragment_display_courses);

        CourseRepository repository = CourseRepository.getInstance();
        Course[] courses = getData(repository);

        Button button = new Button(getActivity());
        button.setBackgroundColor(3);
        button.setText("Helloo");
        button.setTextColor(5);
        layout.addView(button);

//        for (int i=0; i<courses.length; i++) {
//            Button button = new Button(getActivity());
//            button.setTextSize(20);
//            button.setText("HELLOO");
//
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    System.out.println("helllloo");
//                }
//            });
//
//            linearLayout.addView(button);
//        }

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
