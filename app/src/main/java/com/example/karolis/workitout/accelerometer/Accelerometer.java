package com.example.karolis.workitout.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.karolis.workitout.dataobjects.Point;
import com.example.karolis.workitout.utilities.Listener;

import java.util.Calendar;

/**
 * Created by Dziugas on 10/14/2016.
 */

public class Accelerometer implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float updateTime;
    private Point position;
    private Point velocity;
    private Point acceleration;
    private Listener<String> listener;

    public Accelerometer(SensorManager mSensorManager, Listener<String> listener){
        this.mSensorManager = mSensorManager;
        this.mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.listener = listener;

        position = new Point();
        velocity = new Point();
        acceleration = new Point();
    }

    public void startTracking(){
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pauseTracking(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        handleAcceleration(new Point(event.values[0], event.values[1], event.values[2]));
    }

    public void handleAcceleration(Point newAcceleration){
        float timeDelta = getTimeSinceLastUpdate();

        // x = x0 + v0 * t + a * t^2 / 2
        position = position.add(
                velocity.times(timeDelta).add(acceleration.times(timeDelta * timeDelta / 2)));

        // v = v0 + a * t
        velocity = velocity.add(acceleration.times(timeDelta));

        acceleration = newAcceleration;

        listener.notify(formOutput());

        Log.d("Accelerometer", "TimeDelta: " + timeDelta);
        Log.d("Accelerometer", "Position: " + position);
        Log.d("Accelerometer", "Velocity: " + velocity);
        Log.d("Accelerometer", "Acceleration: " + acceleration);
    }

    private float getTimeSinceLastUpdate(){
        float previousUpdateTime = updateTime;
        updateTime = System.nanoTime() / ((float) Math.pow(10, 9));
        return previousUpdateTime == 0 ? 0 : updateTime - previousUpdateTime;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Todo
    }

    private String formOutput(){
        return position.toString("position") + '\n'
            + velocity.toString("velocity") + '\n'
            + acceleration.toString("acceleration");
    }
}
