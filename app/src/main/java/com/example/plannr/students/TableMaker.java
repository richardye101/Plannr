package com.example.plannr.students;

import com.google.firebase.database.core.view.Change;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TableMaker {
    ArrayList<String> table;

    public TableMaker() {
        table = new ArrayList<String>();
    }

    public TableMaker(ArrayList<String> table) {
        this.table = table;
    }

    //
    //we can also just make this return a list or something instead of displaying it
    //TODO: Implement algorithm
    public void makeTable(Student student) {
        //get list of all available courses --or simply use a for each loop if possible

        /*
        for(each course in the list)
            if(course name exists in student profile as taken) --possible use of helper function
                continue;
            else if(course has no prequisites or all prequisates are in student profile as taken) --possible use of helper function
                add to table list
            continue;
         */

        //display the table
    }

    public ArrayList<String> listAvailable() {
        //make List of available courses
        return new ArrayList<String>(); //this return is just temporary until I write the code
    }

    public void displayTable(TableMaker t) {
        //display table

        //may be moved to a different class to adhere to SRP
    }
}
