package com.example.plannr.students;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseCode;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class TableMaker {
    private final ArrayList<CourseCode> table;

    public TableMaker() {
        table = new ArrayList<CourseCode>();
        DatabaseConnection db = DatabaseConnection.getInstance();
        ArrayList<String[]> test = new ArrayList<>();
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
                }
            }
        }
    }

    public ArrayList<String> buildTable(int year) {
        //add error
        ArrayList<String> taken = StudentUserModel.getInstance().getTakenCourses();
        ArrayList<String> toBe = new ArrayList<>();
        ArrayList<CourseCode> fall = new ArrayList<>();
        ArrayList<CourseCode> winter = new ArrayList<>();
        ArrayList<CourseCode> summer = new ArrayList<>();
        boolean failSafe = false;
        int counter = 0;

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

        while(!table.isEmpty() || !failSafe) {
            failSafe = true;
            //all that are availble in fall and can be taken
            //add those to taken
            counter = 0;
            for(CourseCode i : fall){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    taken.add(i.getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i + ":" + "fall" + year);
                    failSafe = false;
                    counter++;
                }
            }
            year++;
            counter = 0;
            //all in winter +1 year, add to taken
            for(CourseCode i : winter){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    taken.add(i.getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i + ":" + "Winter" + year);
                    failSafe = false;
                    counter++;
                }
            }
            //all in summer year add to taken
            counter = 0;
            for(CourseCode i : summer){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    taken.add(i.getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i + ":" + "summer" + year);
                    failSafe = false;
                    counter++;
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
        Collections.addAll(prereqs, c.getCourse().getPrerequisites().split(","));
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
}
