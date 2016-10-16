package com.example.karolis.workitout.dataobjects;

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
}
