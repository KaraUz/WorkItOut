package com.example.karolis.workitout;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.dataobjects.Workout;
import com.example.karolis.workitout.exerciseTrackers.IdleTracker;
import com.example.karolis.workitout.exerciseTrackers.Tracker;
import com.example.karolis.workitout.exerciseTrackers.TrackerFactory;
import com.example.karolis.workitout.utilities.Listener;

import java.util.ListIterator;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity implements Listener<Integer> {
    private TextView counterText;
    private TextView headerView;
    private Tracker tracker;
    private Workout workout;
    private TrackerFactory trackerFactory;
    private ListIterator<Exercise> exerciseIterator;
    private long workoutStartTime;
    private long workoutEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        workout = Workout.exampleWorkout();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        counterText = (TextView)findViewById(R.id.counter_view);
        headerView = (TextView)findViewById(R.id.header_view);

        SensorManager mSensorManager = (SensorManager)
                getSystemService(getApplicationContext().SENSOR_SERVICE);
        trackerFactory = new TrackerFactory(this, mSensorManager);
        tracker = new IdleTracker();
        exerciseIterator = workout.getExercises().listIterator();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tracker.pauseTracking();
    }

    public void startWorkout(View view) {
        startTimeTracking();
        nextExercise();
    }

    public void nextExercise(View view) {
        //TODO: startTimeTracking
        nextExercise();
    }

    public void startTimeTracking(){
        workoutStartTime = System.currentTimeMillis() / 1000;
    }

    public void stopTimeTracking(){
        workoutEndTime = System.currentTimeMillis() / 1000;
    }

    private void nextExercise(){
        if (exerciseIterator.hasNext()){
            Exercise exercise = exerciseIterator.next();
            tracker = trackerFactory.CreateTracker(exercise);

            int exercisesLeft = workout.getExercises().size() - exerciseIterator.previousIndex() - 1;
            setExerciseUI(exercise, exercisesLeft);
            tracker.startTracking();
        }
    }

    private void setExerciseUI(Exercise exercise, int exercisesLeft){
        headerView.setText(exercise.getName());
        TextView exercisesLeftView = (TextView)findViewById(R.id.exercise_count_view);
        exercisesLeftView.setText(exercisesLeft > 0
            ? exercisesLeft + " Exercises left"
            : "Last exercise");

        findViewById(R.id.start_workout_button).setVisibility(View.GONE);
        findViewById(R.id.next_exercise_button).setVisibility(View.GONE);

        counterText.setText(String.format(Locale.ENGLISH, "0 / %1$d",
                tracker.getRequiredRepetitions()));
    }

    private void setInbetweenExercisesUI(){
        headerView.setText(getResources().getString(R.string.ready_for_exercise));
        findViewById(R.id.next_exercise_button).setVisibility(View.VISIBLE);
    }

    private void endExercise(){
        if(exerciseIterator.hasNext()){
            setInbetweenExercisesUI();
        } else {
            stopTimeTracking();
            startEndWorkoutActivity();
        }
    }

    @Override
    public void notify(Integer count) {
        counterText.setText(String.format(Locale.ENGLISH, "%1$d / %2$d", count,
                tracker.getRequiredRepetitions()));

        if (tracker.resultReached()){
            tracker.pauseTracking();
            endExercise();
        }
    }

    private void startEndWorkoutActivity(){
        long workoutDuration = workoutEndTime - workoutStartTime;
        Intent intent = new Intent(this, EndWorkoutActivity.class);
        intent.putExtra("workoutDuration", workoutDuration);
        startActivity(intent);
    }
}
