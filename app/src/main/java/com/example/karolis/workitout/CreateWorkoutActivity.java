package com.example.karolis.workitout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CreateWorkoutActivity extends AppCompatActivity {
    static final int PICK_EXERCISE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
    }

    //Triggers when floating start button is clicked
    public void onClickConfirm(View view){
        finish();
    }
    public void onClickAdd(View view){
        Intent intent = new Intent(this, ChooseExerciseActivity.class);
        startActivityForResult(intent,PICK_EXERCISE_REQUEST);
    }
}
