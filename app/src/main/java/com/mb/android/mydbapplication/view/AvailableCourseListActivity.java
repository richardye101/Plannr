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
import com.mb.android.mydbapplication.view.AvailableCourseDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class AvailableCourseListActivity extends AppCompatActivity {

    private static final String TAG = "AvailableCourseListActivity";

    ListView availableCourseList;
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
        setContentView(R.layout.activity_available_course_list);

        availableCourseList = findViewById(R.id.availableCourseListView);

        ArrayAdapter<String> arr;
        arr   = new ArrayAdapter<String>(
                this,
                R.layout.activity_list_item_view,
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
                Intent intent = new Intent(AvailableCourseListActivity.this,
                        AvailableCourseDetailActivity.class);
                startActivity(intent);

                //pass the course selected to the next detail page
                intent.putExtra("selected", ((TextView) aview).getText());
                intent.putExtra("position", position);

                ArrayList<String> alist = new ArrayList<String>();
                alist.addAll(Arrays.asList(courseAvailable));
                intent.putStringArrayListExtra("com.mb.android.mydbapplication2.view.courses", alist);
                Log.i(TAG, "ArrayList="+alist);
                startActivity(intent);
            }
        };

        availableCourseList.setOnItemClickListener(itemListener);
    }
}