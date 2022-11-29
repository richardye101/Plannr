package com.example.plannr.students;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseCode;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class TableMaker {
    private ArrayList<CourseCode> table;
    private DatabaseConnection db;
    private ArrayList<String[]> test;

    public TableMaker() {
        table = new ArrayList<CourseCode>();
        db = DatabaseConnection.getInstance();
        test = new ArrayList<>();
    }

    public TableMaker(ArrayList<CourseCode> table) {
        this.table = table;
    }

    public void getWhatTake(CourseCode toBe, ArrayList<CourseCode> available) {
        StudentUserModel student = StudentUserModel.getInstance();
        table.add(toBe);

        for(String i: toBe.getCourse().getPrerequisites().split(",")) {
            if(!student.getTakenCourses().contains(i)) {
                for(CourseCode a : available) {
                    if(a.getCourseCode().equals(i)) {
                        if(!table.contains(a)) {
                            this.getWhatTake(a, available);
                        }
                        break;
                    }
                    //else error
                }
            }
        }
    }

    public ArrayList<String> buildTable(int year) {
        ArrayList<String> taken = StudentUserModel.getInstance().getTakenCourses();
        ArrayList<String> toBe = new ArrayList<>();
        ArrayList<CourseCode> fall = new ArrayList<>();
        ArrayList<CourseCode> winter = new ArrayList<>();
        ArrayList<CourseCode> summer = new ArrayList<>();
        for(CourseCode i: table) {
            if(i.getCourse().getFallAvailability()) {
                fall.add(i);
            }
            if(i.getCourse().getWinterAvailability()) {
                winter.add(i);
            }
            if(i.getCourse().getSummerAvailability()) {
                summer.add(i);
            }
        }

        //TODO: add fail safe
        while(!table.isEmpty()) {
            //all that are availble in fall and can be taken
            //add those to taken
            for(CourseCode i : fall){
                if(canTake(i, taken)) {
                    taken.add(i.getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i + ":" + "fall" + year);
                }
            }
            year++;
            //all in winter +1 year, add to taken
            for(CourseCode i : winter){
                if(canTake(i, taken)) {
                    taken.add(i.getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i + ":" + "Winter" + year);
                }
            }
            //all in summer year add to taken
            for(CourseCode i : summer){
                if(canTake(i, taken)) {
                    taken.add(i.getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i + ":" + "summer" + year);
                }
            }
        }
        return toBe;
    }

    public boolean canTake(CourseCode c, ArrayList<String> t) {
        ArrayList<String> prereqs = new ArrayList<>();
        if(c.getCourse().getPrerequisites().split(",")[0].equals("")) {
            return true;
        }
        for(String j : c.getCourse().getPrerequisites().split(",")){
            prereqs.add(j);
        }
        return t.containsAll(prereqs);
    }





    public ArrayList<CourseCode> listAvailable(Iterable<DataSnapshot> snap) {
        ArrayList<CourseCode> courses = new ArrayList<>();

        for(DataSnapshot i: snap) {
            Course course = i.getValue(Course.class);
            courses.add(new CourseCode(course, i.getKey()));

        }
        return courses;
    }

    //temporary
    @Override
    public String toString() {
        String s = "";
        for(CourseCode i: this.table) {
            s += i.getCourseCode() + ", ";
        }
        return s;
    }
}
