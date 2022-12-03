package com.example.plannr.students;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.TableFragment;
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
    private TableMaker table;
    private DatabaseConnection db;
    private TableFragment view;

    public TableMakerPresenter(TableFragment view) {
        table = new TableMaker();
        db = DatabaseConnection.getInstance();
        this.view = view;
    }

    public void generateTable(ArrayList<String> course) {
        DatabaseReference refe = db.ref;
        DatabaseReference offerings = refe.child("offerings");
        offerings.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    ArrayList<CourseHash> h = table.listAvailable(task.getResult().getChildren());
                    for(String s : course) {
                        table.getWhatTake(getCourseFromCode(h, s), h);
                    }
                    HashMap<String, String> temp = needTake(table.buildTable(2022));
                    String[][] ordered = convert(temp);
                    view.addNew("Session", "Courses");
                    int counter = 0;
                    int itt = 0;
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

    public int max(String[][] s) {
        int ret = 0;
        for(String[] i : s) {
            if(Integer.parseInt(i[0]) > ret) {
                ret = Integer.parseInt(i[0]);
            }
        }
        return ret;
    }

    public String[][] convert(HashMap<String, String> list) {
        String[][] s = new String[list.size()][3];
        int counter = 0;
        for(Map.Entry<String, String> i : list.entrySet()) {
            s[counter][0] = i.getKey().split("-")[0];
            s[counter][1] = i.getKey().split("-")[1];
            s[counter][2] = i.getValue();
            counter++;
        }
        return s;
    }

    public CourseHash getCourseFromCode(ArrayList<CourseHash> c, String s) {
        for(CourseHash i: c) {
            if(i.getCourse().getCourseCode().equals(s)) {
                return i;
            }
        }
        return null;
    }

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
