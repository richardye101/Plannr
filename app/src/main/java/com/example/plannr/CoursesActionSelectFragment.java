package com.example.plannr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.databinding.FragmentViewCoursesBinding;
import com.example.plannr.models.Course;
import com.example.plannr.services.CourseRepository;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.util.ButtonGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

/**
 * This fragment shows buttons that allow the user to choose an action regarding courses
 * On "view courses" button click, data is queried from firebase and the user is sent to
 * another fragment
 * Queried courses are stored as arrayList of Course objects in CourseRepository
 */

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

        binding.viewCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
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
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    courses = (Map<String, Object>) task.getResult().getValue();

                    commitData();

                    Toast.makeText(getActivity(),
                            "Database query successful", Toast.LENGTH_SHORT).show();

                    NavHostFragment.findNavController(CoursesActionSelectFragment.this)
                            .navigate(R.id.action_coursesActionSelectFragment_to_DisplayCoursesFragment);
                }
            }
        });
    }

    public void commitData() {
        CourseRepository repository = CourseRepository.getInstance();

        for(Map.Entry<String, Object> entry : courses.entrySet()) {
            String name = ((Map) entry.getValue()).get("courseName").toString();
            String code = entry.getKey();
            String[] prerequisites = ((Map) entry.getValue()).get("courseName").toString().split(";");
            boolean fall = ((Map) entry.getValue()).get("courseName").equals("true");
            boolean summer = ((Map) entry.getValue()).get("courseName").equals("true");
            boolean winter = ((Map) entry.getValue()).get("courseName").equals("true");

            Course temp = new Course(name, code, prerequisites, fall, summer, winter);
            System.out.println(temp.getName() + ", " + temp.getCode() + ", " + temp.getFallAvailablility());

            repository.addCourse(temp);
        }
    }
}
