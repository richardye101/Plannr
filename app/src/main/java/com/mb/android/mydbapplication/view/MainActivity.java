package com.mb.android.mydbapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mb.android.mydbapplication.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onViewChosenCoursesClicked(View v){
        Intent intent = new Intent(MainActivity.this,
                ChosenCourseListActivity.class);
        startActivity(intent);
    }

    public void onAddNewCoursesClicked(View v){
        Log.i(TAG, "clicked add new");
        Intent intent = new Intent(MainActivity.this,
                AvailableCourseListActivity.class);
        startActivity(intent);
    }
}