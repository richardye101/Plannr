package com.example.plannr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.plannr.R;

public class TakenCourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_chosen_course_detail);
    }

    public void onViewReturnClicked(View v) {
        Intent intent = new Intent(TakenCourseDetailActivity.this,
                TakenCourseListActivity.class);
        startActivity(intent);
    }

}