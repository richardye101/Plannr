package com.example.plannr.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plannr.MainActivity;
import com.example.plannr.R;

import java.util.ArrayList;

public class OfferedCourseDetailActivity extends AppCompatActivity {

    private static final String TAG = "OfferedCourseDetailActivity";

    int position = 0;
    ArrayList courses = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_offered_course_detail);

        //read out the parameter from last activity
        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("selected");
        position = extras.getInt("position");
        courses = extras.getStringArrayList("com.mb.android.mydbapplication.view.courses");
        Log.i(TAG,"in onCreate: ---- get array list="+courses);

        //find the TextView to set the value
        //TextView availableCourseCodeInfoTextView = findViewById(R.id.codeInfo);
        //availableCourseCodeInfoTextView.setText(value1);
        TextView offeredCourseNameInfoTextView = findViewById(R.id.nameInfo);
        offeredCourseNameInfoTextView.setText(value1);
        //TextView availableCourseprereqsInfoTextView = findViewById(R.id.prereqsInfo);
        //availableCourseprereqsInfoTextView.setText(value1);
        //TextView availableCoursesessionsInfoTextView = findViewById(R.id.sessionsInfo);
        //availableCoursesessionsInfoTextView.setText(value1);

    }

    public void onAvailableNextClicked(View view){
        TextView offeredCourseTextView = findViewById(R.id.nameInfo);

        Log.i(TAG, "in onAvailableNextClicked: array list="+courses);
        offeredCourseTextView.setText((String)courses.get(position));
        if (courses.size() > position + 1){
            position++;
        }
        else{
            position = 0;
        }

    }

    public void onAvailablePreviousClicked(View view){
        TextView offeredCourseTextView = findViewById(R.id.nameInfo);
        //TextView availableCourseCodeTextView = findViewById(R.id.courseCode);

        Log.i(TAG, "in onAvailablePreviousClicked: array list="+courses);

        if (position > 1){
            position--;
        }
        else{
            position = courses.size() -1;
        }
        offeredCourseTextView.setText((String)courses.get(position));
        //availableCourseCodeTextView.setText((String)courses.get(position));

    }

    public void onAvailableAddClicked(View view){
        TextView offeredCourseTextView = findViewById(R.id.nameInfo);

        Log.i(TAG, "in onAvailableAddClicked: array list="+courses);

        if (position > 1){
            position--;
        }
        else{
            position = courses.size() -1;
        }
        offeredCourseTextView.setText((String)courses.get(position));

    }

    public void onAvailableReturnClicked(View view) {
        Intent intent = new Intent(OfferedCourseDetailActivity.this,
                OfferedCourseListActivity.class);
        startActivity(intent);
    }

    public void onAvailableLogoutClicked(View view){
        Intent intent = new Intent(OfferedCourseDetailActivity.this,
                MainActivity.class);
        startActivity(intent);
    }

}