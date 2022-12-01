package com.mb.android.mydbapplication.view;

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

import com.mb.android.mydbapplication.R;

public class ChosenCourseListActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    ListView chosenCourseListView;
    String[] courseChosen = new String[]{
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
        setContentView(R.layout.activity_chosen_course_list);

        chosenCourseListView = findViewById(R.id.chosenCourseList);

        ArrayAdapter<String> arr;
        arr   = new ArrayAdapter<String>(
                this,
                R.layout.activity_list_item_view,
                courseChosen);
        chosenCourseListView.setAdapter(arr);

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
                Intent intent = new Intent(ChosenCourseListActivity.this,
                        ChosenCourseDetailActivity.class);
                startActivity(intent);
            }
        };

        chosenCourseListView.setOnItemClickListener(itemListener);
    }
}