package com.example.karolis.workitout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.karolis.workitout.dao.Database;
import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.dataobjects.Workout;

import java.util.ArrayList;

public class CreateWorkoutActivity extends AppCompatActivity {
    static final int PICK_EXERCISE_REQUEST = 1;
    Workout workout;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        workout = new Workout(null,new ArrayList<Exercise>());
        adapter = new ArrayAdapter<>(this,
                R.layout.exercise_group_items,workout.getExerciseStringArray());

        ListView listView = (ListView) findViewById(R.id.selectedExerciseList);
        listView.setAdapter(adapter);

    }

    //Triggers when floating start button is clicked
    public void onClickConfirm(View view){
        if(workout == null || workout.getExercises().isEmpty()) {
            Toast.makeText(this, "Empty workouts are not saved!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText workoutName = (EditText) findViewById(R.id.workoutNameInput);
        workout.setName(workoutName.getText().toString());
        if(!new Database(getApplication().openOrCreateDatabase("workout.db", Context.MODE_PRIVATE,null)).saveWorkout(workout)){
            Toast.makeText(this, "Exercise with this name already exists!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    public void onClickAdd(View view){
        Intent intent = new Intent(this, ChooseExerciseActivity.class);
        startActivityForResult(intent,PICK_EXERCISE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_EXERCISE_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                workout.getExercises().add((Exercise)data.getSerializableExtra("exercise"));
                adapter = new ArrayAdapter<>(this,
                        R.layout.exercise_group_items,workout.getExerciseStringArray());

                ListView listView = (ListView) findViewById(R.id.selectedExerciseList);
                listView.setAdapter(adapter);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
