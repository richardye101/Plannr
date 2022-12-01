package com.mb.android.mydbapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mb.android.mydbapplication.R;

public class ChosenCourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_course_detail);
    }

    public void onViewReturnClicked(View v) {
        Intent intent = new Intent(ChosenCourseDetailActivity.this,
                ChosenCourseListActivity.class);
        startActivity(intent);
    }

}