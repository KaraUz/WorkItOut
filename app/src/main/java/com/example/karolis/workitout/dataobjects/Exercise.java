package com.example.karolis.workitout.dataobjects;

/**
 * Created by Karolis on 2016-10-16.
 */

public class Exercise {

    private String name;
    private String description;
    private int difficulty;

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Exercise(String name, String description, int difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
