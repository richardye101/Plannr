package com.example.plannr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plannr.databinding.FragmentViewCoursesBinding;
import com.example.plannr.models.Course;
import com.example.plannr.services.CourseRepository;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.ButtonGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

public class CoursesActionSelectFragment extends Fragment {

    private FragmentViewCoursesBinding binding;
    private DatabaseConnection db;
    private ButtonGenerator buttonGenerator;
    private Map<String, Object> courses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
        binding = FragmentViewCoursesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = DatabaseConnection.getInstance();
        buttonGenerator = ButtonGenerator.getInstance();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        binding.viewCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to DisplayCoursesFragment
            }
        });
    }

    public void getData() {
        db.ref.child("offerings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    courses = null;
                    binding.textView2.setText(courses.get("cscb07").toString());
                }
                else {
                    // do something after completing the task, like sending the data somewhere
                    //this is always a HashMap
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    courses = (Map<String, Object>) task.getResult().getValue();

                    commitData();
                }
            }
        });
    }

    public void commitData() {
        CourseRepository repository = CourseRepository.getInstance();

        for(Map.Entry<String, Object> entry : courses.entrySet()) {
            String name = ((Map) entry.getValue()).get("name").toString();
            String code = entry.getKey();

            repository.addCourse(new Course(name));
        }
    }

//    public void generateButton(String name, String code) {
//        final Button myButton = new Button(myView.getContext());
//        myButton.setText(RAnames[i]);
//        myButton.setId(i + 1);
//    }
}
