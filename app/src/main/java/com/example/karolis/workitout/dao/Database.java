package com.example.karolis.workitout.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.dataobjects.HistoryElement;
import com.example.karolis.workitout.dataobjects.Workout;
import com.example.karolis.workitout.dataobjects.WorkoutResult;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karolis on 2016-10-16.
 */

public class Database implements Serializable{

    private SQLiteDatabase mydatabase;

    public Database(SQLiteDatabase db){
        mydatabase = db;
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Exercise(Name VARCHAR PRIMARY KEY,Description VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS WorkoutExercise(" +
                "WorkoutName VARCHAR," +
                "ExerciseName VARCHAR," +
                "Difficulty integer," +
                "FOREIGN KEY (ExerciseName) REFERENCES Exercise(Name));");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS WorkoutHistory(" +
                "WorkoutName VARCHAR," +
                "WorkoutDate DATETIME," +
                "DurationText VARCHAR," +           // HH:MM:SS
                "DurationInSeconds INTEGER);");
        try{
            mydatabase.execSQL("INSERT INTO Exercise VALUES('Jump exercise','Jump exercise. \n\nEach difficulty level increases amount of jumps required to complete the exercise.');");
            mydatabase.execSQL("INSERT INTO Exercise VALUES('Squat exercise','Squat exercise. \n\nEach difficulty level increases amount of squats required to complete the exercise.');");
            //mydatabase.execSQL("INSERT INTO WorkoutExercise VALUES('Workout2','Exercise1',1);");
            //mydatabase.execSQL("INSERT INTO WorkoutExercise VALUES('Workout2','Exercise1',1);");
        }
        catch(SQLException ex){
            System.out.println("Initial data already exists.");
        }
    }

    public List<Workout> getAllWorkouts(){
        List<Workout> workoutList = new ArrayList<>();
        List<Exercise> exerciseList = new ArrayList<>();
        Exercise exercise;
        String lastWorkout;
        Cursor resultSet = mydatabase.rawQuery("Select WorkoutName,ExerciseName,Difficulty,description from WorkoutExercise, Exercise WHERE ExerciseName = Name Order by WorkoutName",null);
        resultSet.moveToFirst();
        if(resultSet == null || resultSet.getCount() == 0) return workoutList;

        lastWorkout = resultSet.getString(0);
        exercise = new Exercise(resultSet.getString(1),resultSet.getString(3),Integer.valueOf(resultSet.getString(2)));
        exerciseList.add(exercise);
        for (int i = 1; i<resultSet.getCount();i++){
            resultSet.moveToNext();
            exercise = new Exercise(resultSet.getString(1),resultSet.getString(3),Integer.valueOf(resultSet.getString(2)));
            if(lastWorkout.equals(resultSet.getString(0))){
                exerciseList.add(exercise);
            }else{
                workoutList.add(new Workout(lastWorkout,exerciseList));
                exerciseList = new ArrayList<>();
                exerciseList.add(exercise);
                lastWorkout = resultSet.getString(0);
            }
        }
        workoutList.add(new Workout(lastWorkout,exerciseList));
        return workoutList;
    }

    public List<Exercise> getAllExercises(){
        List<Exercise> exerciseList = new ArrayList<>();
        Cursor resultSet = mydatabase.rawQuery("Select * from Exercise",null);
        resultSet.moveToFirst();
        if(resultSet == null || resultSet.getCount() == 0) return exerciseList;
        exerciseList.add(new Exercise(resultSet.getString(0),resultSet.getString(1)));
        for (int i = 1; i<resultSet.getCount();i++){
            resultSet.moveToNext();
            if(resultSet.isAfterLast()) break;
            exerciseList.add(new Exercise(resultSet.getString(0),resultSet.getString(1)));
        }
        return exerciseList;
    }

    public boolean saveWorkout(Workout workout){
        if(workout == null || workout.getExercises().isEmpty()) return false;
        Cursor resultSet = mydatabase.rawQuery("Select WorkoutName from WorkoutExercise where WorkoutName = '"+workout.getName()+"'",null);
        if(resultSet.getCount()>0) return false;
        for (Exercise exercise: workout.getExercises()) {
            mydatabase.execSQL("INSERT INTO WorkoutExercise VALUES('"+workout.getName() +
                    "','" + exercise.getName() + "'," + exercise.getDifficulty() + ");");
        }
        return true;
    }

    public void deleteWorkout(Workout workout){
        mydatabase.delete("WorkoutExercise", "WorkoutName" + "='" + workout.getName()+"'", null);
    }

    public void saveWorkoutResult(WorkoutResult workoutResult){
        mydatabase.insertOrThrow("WorkoutHistory", null, workoutResult.toContentValues());
    }

    public List<HistoryElement> selectWorkoutHistory(int recordCount){
        List<HistoryElement> historyElements = new LinkedList<>();
        Cursor cursor = mydatabase.rawQuery("Select WorkoutName, WorkoutDate, DurationText from WorkoutHistory order by WorkoutDate desc LIMIT "+recordCount, null);
        if(cursor.getCount()<1) return historyElements;
        cursor.moveToFirst();
        historyElements.add(new HistoryElement(cursor.getString(1),cursor.getString(0),cursor.getString(2)));
        while(cursor.moveToNext()){
            historyElements.add(new HistoryElement(cursor.getString(1),cursor.getString(0),cursor.getString(2)));
        }
        return historyElements;
    }
}
