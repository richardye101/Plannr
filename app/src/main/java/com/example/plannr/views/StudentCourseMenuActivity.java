package com.example.plannr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.plannr.R;

public class StudentCourseMenuActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_main);


    }

    public void onViewChosenCoursesClicked(View v){
        Intent intent = new Intent(StudentCourseMenuActivity.this,
                ChosenCourseListActivity.class);
        startActivity(intent);
    }

    public void onAddNewCoursesClicked(View v){
        Log.i(TAG, "clicked add new");
        Intent intent = new Intent(StudentCourseMenuActivity.this,
                AvailableCourseListActivity.class);
        startActivity(intent);
    }
}