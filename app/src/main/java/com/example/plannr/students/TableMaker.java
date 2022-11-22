package com.example.plannr.students;

import com.google.firebase.database.core.view.Change;

import java.util.ArrayList;

public class TableMaker {
    ArrayList<String> table;

    public TableMaker() {
        table = new ArrayList<String>();
    }

    public TableMaker(ArrayList<String> table) {
        this.table = table;
    }

    //TODO: plan how to the data in a consise way on FireBase and how to read it
    //TODO: discuss optimizing the algorithm
    //TODO: Implement
    //we can also just make this return a list or something instead of displaying it
    public void makeTable(Student student) {
        for(ChangeLogger ch : student.change) {
            if(ch.added) {
                //search FireBase for every course with ch.course as a prereq
                //remove from table
            } else {
                //search FireBase for everycourse with ch.course as a prereq
                //add to table
                //search table for ch.course
                //if is is there
                    //remove it
            }
        }
        //display the table
            //we can add another class or method if needed to display the table
    }
}
