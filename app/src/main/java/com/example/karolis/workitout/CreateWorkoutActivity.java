package com.example.karolis.workitout;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.dataobjects.Workout;

import java.util.ArrayList;
import java.util.LinkedList;

public class CreateWorkoutActivity extends AppCompatActivity {
    static final int PICK_EXERCISE_REQUEST = 1;
    Workout workout;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        workout = new Workout(null,new ArrayList<Exercise>());
        //workout.getExercises().add(new Exercise("jump","cool", 5));
        adapter = new ArrayAdapter<>(this,
                R.layout.exercise_listview,workout.getExerciseStringArray());

        ListView listView = (ListView) findViewById(R.id.selectedExerciseList);
        listView.setAdapter(adapter);

    }

    //Triggers when floating start button is clicked
    public void onClickConfirm(View view){
        finish();
    }
    public void onClickAdd(View view){
        Intent intent = new Intent(this, ChooseExerciseActivity.class);
        startActivityForResult(intent,PICK_EXERCISE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                workout.getExercises().add((Exercise)data.getSerializableExtra("exercise"));
                adapter = new ArrayAdapter<>(this,
                        R.layout.exercise_listview,workout.getExerciseStringArray());

                ListView listView = (ListView) findViewById(R.id.selectedExerciseList);
                listView.setAdapter(adapter);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
