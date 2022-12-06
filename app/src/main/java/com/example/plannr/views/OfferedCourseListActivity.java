package com.example.plannr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plannr.R;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OfferedCourseListActivity extends AppCompatActivity {

    private static final String TAG = "AvailableCourseListActivity";
    private DatabaseReference dbRef;

    ListView availableCourseList;
    HashMap<String, OfferedCourse> courses = new HashMap<String, OfferedCourse>();
    ArrayList<String> availableCourseCodeArrayList = new ArrayList<String>();
    ArrayList<String> availableCourseNameArrayList = new ArrayList<String>();
    ArrayList<String> availableCoursePrereqArrayList = new ArrayList<String>();

    String[] courseAvailable = new String[]{
            "Statistics",
            "Calculus 2",
            "Software Design",
            "DM",
            "Statistics",
            "Calculus 2",
            "Software Design",
            "DM",
            "Statistics",
            "Calculus 2",
            "Software Design",
            "DM",
            "Statistics",
            "Calculus 2",
            "Software Design",
            "DM",
            "Statistics",
            "Calculus 2",
            "Software Design",
            "DM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_offered_course_list);

        Log.i(TAG, "readOfferedCourse");
        readOfferedCourse();
        Log.i("COURSE CODE in map: ", courses.keySet().toString());
        courseAvailable = (String[]) courses.keySet().toArray(new String[0]);
        availableCourseList = findViewById(R.id.availableCourseListView);
        Log.i(TAG, "courseAvailable ---"+ availableCourseNameArrayList.size()+courses.size());

        //set up ArrayAdapter
        ArrayAdapter<String> arr;
        arr   = new ArrayAdapter<String>(
                this,
                R.layout.sc_activity_list_item_view,
                courseAvailable);

        availableCourseList.setAdapter(arr);

        // Define the listener interface
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View aview,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) aview).getText() + " is available!",
                        Toast.LENGTH_SHORT).show();

                //go to detail
                Log.i(TAG, "clicked to go to detail");
                Intent intent = new Intent(OfferedCourseListActivity.this,
                        OfferedCourseDetailActivity.class);
                //startActivity(intent);

                //pass the course selected to the next detail page
                intent.putExtra("selected", ((TextView) aview).getText());
                intent.putExtra("position", position);

                ArrayList<String> alist = new ArrayList<String>();
                alist.addAll(Arrays.asList(courseAvailable));
                intent.putStringArrayListExtra("com.mb.android.mydbapplication.view.courses", alist);
                Log.i(TAG, "ArrayList=");
                startActivity(intent);
            }
        };

        availableCourseList.setOnItemClickListener(itemListener);
    }

    public void readOfferedCourse(){
        DatabaseConnection db = new DatabaseConnection();
        dbRef = db.ref.child("offerings");

        Log.i(TAG, "in readOfferedCourse 1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Log.i(TAG, "in readOfferedCourse 2");
                    for(DataSnapshot d : snapshot.getChildren()){

                        Log.i("COURSE CODE", d.child("courseCode").getValue(String.class));

                        String code = d.child("courseCode").getValue(String.class);
                        String name = d.child("courseName").getValue(String.class);
                        String prereq = d.child("preRequisites").getValue(String.class);
                        //String id = Long.toString(d.child("id").getValue(Long.class));
                        courses.put(code +" - " + name, new OfferedCourse(code, "id", name, prereq));
                        //Log.i("COURSE CODE in map: ", courses.keySet().toString());

                    }

                    Log.i("COURSE CODE in map: ", courses.keySet().toString());
                    courseAvailable = (String[]) courses.keySet().toArray(new String[0]);
                    availableCourseList = findViewById(R.id.availableCourseListView);
                    Log.i(TAG, "courseAvailable ---"+ availableCourseNameArrayList.size()+courses.size());

                    //set up ArrayAdapter
                    ArrayAdapter<String> arr =
                            new ArrayAdapter<String>(OfferedCourseListActivity.this,
                                    R.layout.sc_activity_list_item_view,
                                    courseAvailable);

                    availableCourseList.setAdapter(arr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OfferedCourseListActivity.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}