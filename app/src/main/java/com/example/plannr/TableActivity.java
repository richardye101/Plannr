package com.example.plannr;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseHash;
import com.example.plannr.databinding.ActivityTableBinding;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.example.plannr.students.TableMaker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class TableActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTableBinding binding;
    public TableMaker table = new TableMaker();
    private DatabaseConnection db;

    Button gbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        db = DatabaseConnection.getInstance();
        //
        //this is just test code
        /*
        ArrayList<String> d = new ArrayList<>();
        d.add("MATA31");
        StudentUserModel.getInstance().setTakenCourses(d);
        */

        gbutton = (Button) findViewById(R.id.generateButton);
        binding = ActivityTableBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        changeText();
    }

    private void changeText() {
        TextView t = (TextView) findViewById(R.id.testerBum);

        gbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentUserModel s = StudentUserModel.getInstance();
                DatabaseReference refe = db.ref;
                DatabaseReference offerings = refe.child("offerings");
                offerings.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            //t.setText(task.getResult().getChildren().getClass().toString());
                            //////table.getWhatTake(new CourseCode(new Course("MATB41", "calc", true, false, false, "MATA31"), "MATB42"), table.listAvailable(task.getResult().getChildren()));
                            table.getWhatTake(new CourseHash( new Course("CSCA48", "CS 2", false, true, true, ",1996865366"), "1996865490"), table.listAvailable(task.getResult().getChildren()));
                            String test = "";
                            for(String s : table.buildTable(2022)) {
                                test = test + s + " ";
                            }
                            t.setText(test);
                        }
                        //return courses
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}