package com.example.plannr.students;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseHash;
import com.example.plannr.models.StudentUserModel;
import com.example.plannr.services.DatabaseConnection;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class TableMaker {
    private final ArrayList<CourseHash> table;

    public TableMaker() {
        table = new ArrayList<CourseHash>();
        DatabaseConnection db = DatabaseConnection.getInstance();
        ArrayList<String[]> test = new ArrayList<>();
    }

    public void getWhatTake(CourseHash toBe, ArrayList<CourseHash> available) {
        StudentUserModel student = StudentUserModel.getInstance();
        table.add(toBe);

        for(String i: Course.stringToArraylist(toBe.getCourse().getPrerequisites())) {
            if(!student.getTakenCourses().contains(i)) {
                for(CourseHash a : available) {
                    if(a.getCourseHash().equals(i)) {
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
        ArrayList<CourseHash> fall = new ArrayList<>();
        ArrayList<CourseHash> winter = new ArrayList<>();
        ArrayList<CourseHash> summer = new ArrayList<>();
        boolean failSafe = false;
        int counter = 0;

        for(CourseHash i: table) {
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
            for(CourseHash i : fall){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    taken.add(i.getCourse().getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i.getCourse().getCourseCode() + ":" + "fall" + year);
                    failSafe = false;
                    counter++;
                }
            }
            year++;
            counter = 0;
            //all in winter +1 year, add to taken
            for(CourseHash i : winter){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    taken.add(i.getCourse().getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i.getCourse().getCourseCode() + ":" + "Winter" + year);
                    failSafe = false;
                    counter++;
                }
            }
            //all in summer year add to taken
            counter = 0;
            for(CourseHash i : summer){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    taken.add(i.getCourse().getCourseCode());
                    fall.remove(i);
                    table.remove(i);
                    winter.remove(i);
                    summer.remove(i);
                    toBe.add(i.getCourse().getCourseCode() + ":" + "summer" + year);
                    failSafe = false;
                    counter++;
                }
            }
        }
        return toBe;
    }

    public boolean canTake(CourseHash c, ArrayList<String> t) {
        ArrayList<String> prereqs = new ArrayList<>();
        if(c.getCourse().getPrerequisites().split(",")[0].equals("")) {
            return true;
        }
        Collections.addAll(prereqs, c.getCourse().getPrerequisites().split(","));
        return t.containsAll(prereqs);
    }

    public ArrayList<CourseHash> listAvailable(Iterable<DataSnapshot> snap) {
        ArrayList<CourseHash> courses = new ArrayList<>();

        for(DataSnapshot i: snap) {
            courses.add(new CourseHash(i.getValue(Course.class), i.getKey()));
        }
        return courses;
    }
}
