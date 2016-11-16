package com.example.karolis.workitout.dataobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karolis on 2016-10-16.
 */

public class Workout {
    private String name;
    private List<Exercise> exercises;

    public Workout(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public static Workout exampleWorkout(){
        List<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("jumpExercise", "", 1));
        exercises.add(new Exercise("squatExercise", "", 1));
        return new Workout("example workout", exercises);
    }

    public String[] getExerciseStringArray(){
        String[] strings = new String[exercises.size()];
        for (int i = 0; i<exercises.size();i++){
            strings[i] = exercises.get(i).toString();
        }
        return strings;
    }
}
