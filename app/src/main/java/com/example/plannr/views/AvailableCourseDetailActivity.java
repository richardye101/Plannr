package com.example.plannr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.plannr.R;

import java.util.ArrayList;

public class AvailableCourseDetailActivity extends AppCompatActivity {

    private static final String TAG = "AvailableCourseDetailActivity";

    int position = 0;
    ArrayList courses = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_available_course_detail);

        //read out the parameter from last activity
        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("selected");
        position = extras.getInt("position");
        courses = extras.getStringArrayList("com.mb.android.mydbapplication.view.courses");
        Log.i(TAG,"get array list="+courses);

        //find the TextView to set the value
        //TextView availableCourseCodeInfoTextView = findViewById(R.id.codeInfo);
        //availableCourseCodeInfoTextView.setText(value1);
        TextView availableCourseNameInfoTextView = findViewById(R.id.nameInfo);
        /*availableCourseNameInfoTextView.setText(value1);
        TextView availableCourseprereqsInfoTextView = findViewById(R.id.prereqsInfo);
        availableCourseprereqsInfoTextView.setText(value1);
        TextView availableCoursesessionsInfoTextView = findViewById(R.id.sessionsInfo);
        availableCoursesessionsInfoTextView.setText(value1);*/



    }

    public void onAvailableNextClicked(View view){
        TextView availableCourseTextView = findViewById(R.id.nameInfo);

        Log.i(TAG, "array list="+courses);
        availableCourseTextView.setText((String)courses.get(position));
        if (courses.size() > position + 1){
            position++;
        }
        else{
            position = 0;
        }

    }

    public void onAvailablePreviousClicked(View view){
        TextView availableCourseTextView = findViewById(R.id.nameInfo);

        Log.i(TAG, "array list="+courses);

        if (position > 1){
            position--;
        }
        else{
            position = courses.size() -1;
        }
        availableCourseTextView.setText((String)courses.get(position));

    }

    public void onAvailableAddClicked(View view){
        TextView availableCourseTextView = findViewById(R.id.nameInfo);

        Log.i(TAG, "array list="+courses);

        if (position > 1){
            position--;
        }
        else{
            position = courses.size() -1;
        }
        availableCourseTextView.setText((String)courses.get(position));

    }
}