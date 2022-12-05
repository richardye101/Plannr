package com.example.plannr.students;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.TableFragment;
import com.example.plannr.TableInputFragment;
import com.example.plannr.course.CourseHash;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableMakerPresenter {
    private final TableMaker table;
    private final DatabaseConnection db;
    private final TableFragment view;

    public TableMakerPresenter(TableFragment view) {
        table = new TableMaker();
        db = DatabaseConnection.getInstance();
        this.view = view;
    }

    /**
     * generates table and displays it based on input given
     * @param course List of Strings representing input courses
     */
    public void generateTable(ArrayList<String> course) {
        //connect to databse
        DatabaseReference refe = db.ref;
        DatabaseReference offerings = refe.child("offerings");
        offerings.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //if read failed
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else { //read success
                    ArrayList<CourseHash> h = table.listAvailable(task.getResult().getChildren());

                    //generate list of all courses needed to be taken
                    for(String s : course) {
                        try {
                            table.getWhatTake(getCourseFromCode(h, s), h);
                        } catch (PrerequisiteException e) {
                            //toast
                            TableInputFragment.toast("Error: Prerequisite course could not " +
                                    "be found - Contact an Admin");
                            return;
                        }
                    }

                    //create hashmap of courses to be taken and when
                    HashMap<String, String> temp = null;
                    try {
                        temp = needTake(table.buildTable(2022));
                    } catch (PrerequisiteException e) {
                        TableInputFragment.toast("Error: Prerequisites may have an error " +
                                "- Contact an Admin");
                        return;
                    }
                    //convert hashmap into 2d array to get order
                    String[][] ordered = convert(temp);

                    //add new view for column labels
                    view.addNew("Session", "Courses");

                    //display in correct order
                    int counter = 0;
                    int max = max(ordered);
                    while(counter <= max) {
                        for(String[] i : ordered) {
                            if(Integer.parseInt(i[0]) == counter) {
                                view.addNew(i[1], i[2]);
                            }
                        }
                        counter++;
                    }
                }
            }
        });
    }

    /**
     * finds the max value of order to be displayed
     * @param s 2d Array with Courses to be taken, when to take them, and display order
     * @return max of display order
     */
    public int max(String[][] s) {
        int ret = 0;
        for(String[] i : s) {
            if(Integer.parseInt(i[0]) > ret) {
                ret = Integer.parseInt(i[0]);
            }
        }
        return ret;
    }

    /**
     * converts a hashmap to 2d array
     * @param list hashmap with key when to take them, value courses to be taken in that session
     * @return 2d array
     */
    public String[][] convert(HashMap<String, String> list) {
        String[][] s = new String[list.size()][3];
        int counter = 0;
        for(Map.Entry<String, String> i : list.entrySet()) {
            //order to display
            s[counter][0] = i.getKey().split("-")[0];

            //when to take
            s[counter][1] = i.getKey().split("-")[1];

            //course
            s[counter][2] = i.getValue();
            counter++;
        }
        return s;
    }

    /**
     * gets course object from coursecode string
     * @param c list of all courses
     * @param s String of coursecode
     * @return CourseHash of corresponding coursecode
     */
    public CourseHash getCourseFromCode(ArrayList<CourseHash> c, String s) {
        for(CourseHash i: c) {
            if(i.getCourse().getCourseCode().equals(s)) {
                return i;
            }
        }
        return null;
    }

    /**
     * generates a hashmap with key session and year, value courses to take in session
     * @param need list of courses needed to be taken and when to take them
     * @return HashMap
     */
    public HashMap<String, String> needTake(ArrayList<String> need) {
        HashMap<String, String> list = new HashMap<>();
        for(String i : need) {
            String[] s = i.split(":");
            if(!list.containsKey(s[1])) {
                list.put(s[1], s[0]);
            } else {
                String temp = list.get(s[1]);
                list.remove(s[1]);
                list.put(s[1], temp + ", " + s[0]);
            }
        }
        return list;
    }
}
