package com.example.plannr.students;

import com.example.plannr.course.Course;
import com.example.plannr.course.CourseHash;
import com.example.plannr.models.StudentUserModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class TableMaker {
    private final ArrayList<CourseHash> table;

    boolean failsafe = true;
    public TableMaker() {
        table = new ArrayList<>();
    }

    /**
     * generates list of courses that need to be taken for a specific course and saves them
     * in field table
     * multiple courses will not be added to table if they are the same course
     * @param toBe course wanted to be taken
     * @param available list of all available courses
     */
    public void getWhatTake(CourseHash toBe, ArrayList<CourseHash> available) throws PrerequisiteException {
        StudentUserModel student = StudentUserModel.getInstance();

        //if course is already in table, do not add and do nothing
        if(table.contains(toBe)) {
            return;
        }

        //add to table
        table.add(toBe);

        //check through prerequisites and recursively call getWhatTake on prerequisites
        for(String i: Course.stringToArraylist(toBe.getCourse().getPrerequisites())) {
            if(!student.getTakenCourses().contains(i)) {
                failsafe = true;
                for(CourseHash a : available) {
                    if(a.getCourseHash().equals(i)) {
                        failsafe = false;
                        if(!table.contains(a)) {
                            this.getWhatTake(a, available);
                        }
                        break;
                    }
                }
                if(failsafe) {
                    //toast
                    table.clear();
                    throw new PrerequisiteException("No Course exists for prerequisite");
                }
            }
        }
    }

    /**
     * generates ArrayList of all courses to be taken and when to take them
     * @param year year of first fall session
     * @return ArrayList of all courses to be taken and when to take them as String
     */
    public ArrayList<String> buildTable(int year) throws PrerequisiteException {
        //initialize all needed variables
        ArrayList<String> taken = new ArrayList<>(StudentUserModel.getInstance().getTakenCoursesList());

        ArrayList<String> toBe = new ArrayList<>();
        ArrayList<CourseHash> fall = new ArrayList<>();
        ArrayList<CourseHash> winter = new ArrayList<>();
        ArrayList<CourseHash> summer = new ArrayList<>();
        ArrayList<String> tooAdd = new ArrayList<>();
        ArrayList<CourseHash> tooRemove = new ArrayList<>();
        boolean failSafe = false;
        int order = 0;
        int counter;

        //sort courses into when they are available
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

        while(!table.isEmpty() && !failSafe) {
            failSafe = true;
            //all that are available in fall and can be taken
            //add those to taken
            counter = 0;

            //check what fall courses can be taken
            for(CourseHash i : fall){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    tooAdd.add(i.getCourseHash());
                    tooRemove.add(i);
                    toBe.add(i.getCourse().getCourseCode() + ":" + order + "-" + "Fall " + year);
                    failSafe = false;
                    counter++;
                }
            }
            //remove from all lists courses that were just taken in fall
            //add to courses that were just taken into taken list
            taken.addAll(tooAdd);
            fall.removeAll(tooRemove);
            winter.removeAll(tooRemove);
            summer.removeAll(tooRemove);
            table.removeAll(tooRemove);
            tooAdd.clear();
            tooRemove.clear();

            //increment year counter
            year++;

            counter = 0;
            order++;

            //repeat for winter courses
            for(CourseHash i : winter){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    tooAdd.add(i.getCourseHash());
                    tooRemove.add(i);
                    toBe.add(i.getCourse().getCourseCode() + ":" +  order + "-" + "Winter " + year);
                    failSafe = false;
                    counter++;
                }
            }
            taken.addAll(tooAdd);
            fall.removeAll(tooRemove);
            winter.removeAll(tooRemove);
            summer.removeAll(tooRemove);
            table.removeAll(tooRemove);
            tooAdd.clear();
            tooRemove.clear();

            //repeat for summer courses
            counter = 0;
            order++;
            for(CourseHash i : summer){
                if(counter >= 6) {
                    break;
                }
                if(canTake(i, taken)) {
                    tooAdd.add(i.getCourseHash());
                    tooRemove.add(i);
                    toBe.add(i.getCourse().getCourseCode() + ":" +  order + "-" + "Summer " + year);
                    failSafe = false;
                    counter++;
                }
            }
            taken.addAll(tooAdd);
            fall.removeAll(tooRemove);
            winter.removeAll(tooRemove);
            summer.removeAll(tooRemove);
            table.removeAll(tooRemove);
            tooAdd.clear();
            tooRemove.clear();

        }
        if(failSafe) {
            throw new PrerequisiteException("Error with prerequisites");
        }
        return toBe;
    }


    /**
     * checks if a course can be taken
     * @param c course to be taken
     * @param t list of courses taken
     * @return true if c can be taken, false otherwise
     */
    public boolean canTake(CourseHash c, ArrayList<String> t) {
        ArrayList<String> prereqs = new ArrayList<>();

        //if no prerequisites return true
        if(c.getCourse().getPrerequisites().equals("")) {
            return true;
        }

        //add all prerequisites to list
        Collections.addAll(prereqs, c.getCourse().getPrerequisites().split(","));

        //remove empty string
        prereqs.remove("");

        //compare
        return t.containsAll(prereqs);
    }

    /**
     * list all available courses
     * @param snap iterable DataSnapshot generated when reading database
     * @return ArrayList of all courses being offered
     */
    public ArrayList<CourseHash> listAvailable(Iterable<DataSnapshot> snap) {
        ArrayList<CourseHash> courses = new ArrayList<>();

        for(DataSnapshot i: snap) {
            courses.add(new CourseHash(i.getValue(Course.class), i.getKey()));
        }
        return courses;
    }
}
