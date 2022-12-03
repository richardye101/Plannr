package com.example.plannr.students;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.example.plannr.R;
import com.example.plannr.TableInputFragment;
import com.example.plannr.admin.FirebaseCallback;
import com.example.plannr.course.Course;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TableInputPresenter {
    private static ArrayList<String> input;
    private TableInputFragment view;
    private DatabaseConnection db;

    public TableInputPresenter(TableInputFragment view){
        this.view = view;
        db = DatabaseConnection.getInstance();
    }

    public void validate(String s, TableInputFragment frag) {
        Course course = new Course();
        ArrayList<String> given = Course.stringToArraylist(s);
        readData(new FirebaseCallback() {
            @Override
            public void onCallBack(HashMap<String, String> list) {

                int count = 0;

                for(int i = 0; i < given.size(); i++){
                    if(list.containsValue(given.get(i))){
                        count ++;
                    }
                }

                if(count == given.size()){
                    setInput(given);
                    String t = "good";
                    view.getTest().setText(t);
                    NavHostFragment.findNavController(frag)
                            .navigate(R.id.action_tableInputFragment_to_tableFragment);
                }
                else {
                    view.getTableInputView().setError("Invalid Courses selection");
                }
            }
        });
    }


    public void readData(FirebaseCallback firebaseCallback){

        //Configure database path and text reference
        DatabaseReference ref = db.ref.child("offerings");

        HashMap<String, String> dbCourses = new HashMap<String, String>();

        //Scrape the available from database
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot d : snapshot.getChildren()){
                        Course course = d.getValue(Course.class);
                        Log.i("COURSE CODE", course.getCourseCode());
                        dbCourses.put(d.getKey(), course.getCourseCode());
                    }
                    firebaseCallback.onCallBack(dbCourses);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error: ", error.getMessage());
            }

        });
    }

    public static ArrayList<String> getInput() {
        return input;
    }

    private static void setInput(ArrayList<String> s) {
        input = s;
    }
}
