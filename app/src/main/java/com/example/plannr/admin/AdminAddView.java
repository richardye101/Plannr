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
 * AdminAddView class responsible for getting the information from the input text fields
 */

public class AdminAddView extends AppCompatActivity {

    AdminAddPresenter presenter;
    DatabaseConnection db;

    public AdminAddView(){
        db = DatabaseConnection.getInstance();
        presenter = new AdminAddPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_view);

        Button addButton = findViewById(R.id.adminAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addCourse();
            }
        });
    }

    public String getCourseName(){
        EditText coursename = findViewById(R.id.adminCourseNameField);
        return coursename.getText().toString();
    }

    public String getCourseCode(){
        EditText coursecode = findViewById(R.id.adminCourseCodeField);
        return coursecode.getText().toString();
    }

    public String getAvailability(){
        String availability = "";

        CheckBox fall = findViewById(R.id.fall);
        CheckBox winter = findViewById(R.id.winter);
        CheckBox summer = findViewById(R.id.summer);

        if(fall.isChecked()){
            availability = availability + "Fall";
        }
        if(winter.isChecked()){
            availability = availability + "/Winter";
        }
        if(summer.isChecked()){
            availability = availability + "/Summer";
        }

        return availability;
    }

    public String getPrerequisite(){
        EditText prerequisite = findViewById(R.id.adminPrerequisiteField);
        return prerequisite.getText().toString();
    }

    //public Button getButtonRef(){
    //    Button addButton = findViewById(R.id.adminAddButton);
   //    return addButton;
    //}

}