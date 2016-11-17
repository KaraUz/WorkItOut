package com.example.karolis.workitout.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.dataobjects.Workout;
import com.example.karolis.workitout.dataobjects.WorkoutResult;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Karolis on 2016-10-16.
 */

public class Database {

    SQLiteDatabase mydatabase;

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
        //mydatabase.execSQL("INSERT INTO Exercise VALUES('Exercise1','This is first test exercise');");
        //mydatabase.execSQL("INSERT INTO WorkoutExercise VALUES('Workout2','Exercise1',1);");
        //mydatabase.execSQL("INSERT INTO WorkoutExercise VALUES('Workout2','Exercise1',1);");
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
        Exercise exercise;
        Cursor resultSet = mydatabase.rawQuery("Select * from Exercise by WorkoutName",null);
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

    public void saveWorkoutResult(WorkoutResult workoutResult){
        mydatabase.insertOrThrow("WorkoutHistory", null, workoutResult.toContentValues());

//        Cursor cursor = mydatabase.rawQuery("Select WorkoutName, WorkoutDate, DurationText, DurationInSeconds from WorkoutHistory", null);
//        cursor.moveToFirst();
//        String workoutName = cursor.getString(0);
//        String workoutDate = cursor.getString(1);
//        String durationText = cursor.getString(2);
//        String durationInSeconds = cursor.getString(3);
//        System.out.println(workoutName);
    }
}
