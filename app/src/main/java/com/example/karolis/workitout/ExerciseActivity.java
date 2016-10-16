package com.example.karolis.workitout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.karolis.workitout.accelerometer.Accelerometer;
import com.example.karolis.workitout.dataobjects.Point;
import com.example.karolis.workitout.utilities.Listener;

import java.util.Calendar;

public class ExerciseActivity extends AppCompatActivity implements Listener<String> {
    private SensorManager mSensorManager;
    private TextView text;
    private Accelerometer accelerometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        text = (TextView)findViewById(R.id.text_field);
        text.setText("event not triggered");

        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        accelerometer = new Accelerometer(mSensorManager, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.startTracking();
        //testAccelerometer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.pauseTracking();
    }

    @Override
    public void notify(String s) {
        text.setText(s);
    }

    public void testAccelerometer(){
        accelerometer.handleAcceleration(new Point(10, 10, 10));
        try {
            Thread.sleep(1000);
            accelerometer.handleAcceleration(new Point(0, 0, 0));
            Thread.sleep(1000);
            accelerometer.handleAcceleration(new Point(-10, -10, -10));
            Thread.sleep(1000);
            accelerometer.handleAcceleration(new Point(0, 0, 0));
            Thread.sleep(1000);
            accelerometer.handleAcceleration(new Point(0, 0, 0));
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }
}
