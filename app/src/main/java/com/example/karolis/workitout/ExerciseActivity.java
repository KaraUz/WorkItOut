package com.example.karolis.workitout;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.karolis.workitout.exerciseTrackers.JumpTracker;
import com.example.karolis.workitout.exerciseTrackers.SquatTracker;
import com.example.karolis.workitout.utilities.Listener;

public class ExerciseActivity extends AppCompatActivity implements Listener<String> {
    private SensorManager mSensorManager;
    private TextView text;
    private SquatTracker squatTracker;
    private JumpTracker jumpTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        text = (TextView)findViewById(R.id.text_field);
        text.setText("event not triggered");

        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
       // squatTracker = new SquatTracker(mSensorManager, this);
        jumpTracker = new JumpTracker(mSensorManager, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //squatTracker.startTracking();
        jumpTracker.startTracking();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //squatTracker.pauseTracking();
        jumpTracker.pauseTracking();
    }

    @Override
    public void notify(String s) {
        text.setText(s);
    }
}
