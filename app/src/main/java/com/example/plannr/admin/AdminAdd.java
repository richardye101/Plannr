package com.example.plannr.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.plannr.R;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DatabaseReference;

/**
 * Admin add class responsible for taking inputted information and adding it to the
 * admin offerings section in the database
 */

public class AdminAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        //get reference to the views
        EditText coursename = (EditText) findViewById(R.id.adminCourseNameField);
        EditText coursecode = (EditText) findViewById(R.id.adminCourseCodeField);
        CheckBox fall = (CheckBox) findViewById(R.id.fall);
        CheckBox winter = (CheckBox) findViewById(R.id.winter);
        CheckBox summer = (CheckBox) findViewById(R.id.summer);
        EditText prerequisite = (EditText) findViewById(R.id.adminPrerequisiteField);
        Button add = (Button) findViewById(R.id.adminAddButton);

        //get database reference
        DatabaseConnection db = new DatabaseConnection();
        DatabaseReference ref = db.ref;

        //Create a button listener event
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //reconfigure the reference to point to the offerings section of the database
                DatabaseReference offeringsRef = ref.child("offerings");

                //Create key
                String key = coursecode.getText().toString();

                //Create availability string
                String availability = "";

                if(fall.isChecked()){
                    availability = availability + "Fall";
                }
                if(winter.isChecked()){
                    availability = availability + "/Winter";
                }
                if(summer.isChecked()){
                    availability = availability + "/Summer";
                }

                //update database
                offeringsRef.child(key).child("availability").setValue(availability);

                offeringsRef.child(key).child("name").setValue(coursename.getText().toString());

                offeringsRef.child(key).child("prerequisite").setValue(prerequisite.getText().toString());

            }
        });



    }
}