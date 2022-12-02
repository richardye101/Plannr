package com.example.plannr.views;

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
        setContentView(R.layout.sc_activity_available_course_list);

        availableCourseList = findViewById(R.id.availableCourseListView);

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

    public static class AvailableCourseDetailActivity extends AppCompatActivity {

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
}