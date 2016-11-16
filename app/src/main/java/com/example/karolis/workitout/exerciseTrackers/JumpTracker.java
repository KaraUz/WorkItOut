package com.example.karolis.workitout.exerciseTrackers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.karolis.workitout.dataobjects.Point;
import com.example.karolis.workitout.utilities.Listener;

/**
 * Created by Dziugas on 11/4/2016.
 */

public class JumpTracker implements SensorEventListener, Tracker {
    private SensorManager mSensorManager;
    private Sensor accelerometerSensor;
    private Sensor gravitySensor;
    private Sensor magneticSensor;
    private Point acceleration;
    private Listener<Integer> listener;
    private int jumpCount;
    private boolean inAJump;
    private float[] gravityValues;
    private float[] magneticValues;
    private int requiredRepetitions;

    public JumpTracker(SensorManager mSensorManager, Listener<Integer> listener, int difficulty){
        this.mSensorManager = mSensorManager;
        this.accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.gravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        this.magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.listener = listener;

        jumpCount = 0;
        inAJump = false;
        acceleration = new Point();
        requiredRepetitions = countRequiredRepetitions(difficulty);
    }

    public void startTracking(){
        mSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pauseTracking(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((gravityValues != null) && (magneticValues != null)
                && (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)) {

            handleAcceleration(getEarthOrientatedAcceleration(event));

        } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            gravityValues = event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticValues = event.values;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    private Point getEarthOrientatedAcceleration(SensorEvent event){
        float[] deviceRelativeAcceleration = new float[4];
        deviceRelativeAcceleration[0] = event.values[0];
        deviceRelativeAcceleration[1] = event.values[1];
        deviceRelativeAcceleration[2] = event.values[2];
        deviceRelativeAcceleration[3] = 0;

        float[] R = new float[16], I = new float[16], earthAcc = new float[16];

        SensorManager.getRotationMatrix(R, I, gravityValues, magneticValues);

        float[] inv = new float[16];

        android.opengl.Matrix.invertM(inv, 0, R, 0);
        android.opengl.Matrix.multiplyMV(earthAcc, 0, inv, 0, deviceRelativeAcceleration, 0);
        return new Point(earthAcc[0], earthAcc[1], earthAcc[2]);
    }

    public void handleAcceleration(Point newAcceleration){
        jumpCount += jumpStarted(newAcceleration) ? 1 : 0;
        acceleration = newAcceleration;

        listener.notify(formJumpOutput());
    }

    private boolean jumpStarted(Point newAcceleration){
        if (inAJump){
            inAJump = newAcceleration.getZ() < 9;
            return false;
        } else {
            inAJump = newAcceleration.getZ() < 0;
            return inAJump;
        }
    }

    private Integer formJumpOutput(){
        return jumpCount;
    }

    private int countRequiredRepetitions(int difficulty){
        return difficulty * 15;
    }

    @Override
    public boolean resultReached() {
        return requiredRepetitions <= jumpCount;
    }

    @Override
    public int getRequiredRepetitions() {
        return requiredRepetitions;
    }
}
