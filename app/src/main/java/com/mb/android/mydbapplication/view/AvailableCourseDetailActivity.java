package com.mb.android.mydbapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mb.android.mydbapplication.R;

import java.util.ArrayList;

public class AvailableCourseDetailActivity extends AppCompatActivity {

    private static final String TAG = "AvailableCourseDetailActivity";

    int position = 0;
    ArrayList courses = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_course_detail);

        //read out the parameter from last activity
        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("selected");
        position = extras.getInt("position");
        courses = extras.getStringArrayList("com.mb.android.mydbapplication2.view.courses");
        Log.i(TAG,"get array list="+courses);

        //find the TextView to set the value
        TextView availableCourseTextView = findViewById(R.id.nameInfo);
        availableCourseTextView.setText(value1);

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
        TextView availableCourseCodeTextView = findViewById(R.id.courseCode);

        Log.i(TAG, "array list="+courses);

        if (position > 1){
            position--;
        }
        else{
            position = courses.size() -1;
        }
        availableCourseTextView.setText((String)courses.get(position));
        availableCourseCodeTextView.setText((String)courses.get(position));

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

    public void onViewReturnClicked(View v) {
        Intent intent = new Intent(AvailableCourseDetailActivity.this,
                AvailableCourseListActivity.class);
        startActivity(intent);
    }

}