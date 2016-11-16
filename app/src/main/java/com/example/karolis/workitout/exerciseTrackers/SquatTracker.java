package com.example.karolis.workitout.exerciseTrackers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.karolis.workitout.dataobjects.Point;
import com.example.karolis.workitout.utilities.Listener;

/**
 * Created by Dziugas on 10/14/2016.
 */

public class SquatTracker implements SensorEventListener, Tracker {

    private final float Z_THRESHOLD = 0.75f;
    private final float TIME_THRESHOLD = 0.8f;
    private SensorManager mSensorManager;
    private Sensor accelerometerSensor;
    private Sensor gravitySensor;
    private Sensor magneticSensor;
    private float updateTime;
    private Point position;
    private Point velocity;
    private Point acceleration;
    private Listener<Integer> listener;
    private int directionChangeCount;
    private float directionChangeTime;
    private float[] gravityValues;
    private float[] magneticValues;
    private Point orientation;
    private int requiredRepetitions;

    public SquatTracker(SensorManager mSensorManager, Listener<Integer> listener, int difficulty){
        this.mSensorManager = mSensorManager;
        this.accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.gravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        this.magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.listener = listener;

        position = new Point();
        velocity = new Point();
        acceleration = new Point();
        orientation = new Point();
        directionChangeCount = 0;
        directionChangeTime = 0;
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
                && (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)) {

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

        setOrientation(R);

        float[] inv = new float[16];

        android.opengl.Matrix.invertM(inv, 0, R, 0);
        android.opengl.Matrix.multiplyMV(earthAcc, 0, inv, 0, deviceRelativeAcceleration, 0);
        return new Point(earthAcc[0], earthAcc[1], earthAcc[2]);
    }

    private void setOrientation(float[] R){
        float[] orientationValues = new float[3];
        SensorManager.getOrientation(R, orientationValues);
        orientation = new Point(orientationValues[0], orientationValues[1], orientationValues[2]);
    }

    public void handleAcceleration(Point newAcceleration){
        float timeDelta = getTimeSinceLastUpdate();

        // x = x0 + v0 * t + a * t^2 / 2
        position = position.add(
                velocity.times(timeDelta).add(acceleration.times(timeDelta * timeDelta / 2)));

        // v = v0 + a * t
        velocity = velocity.add(acceleration.times(timeDelta));

        directionChangeCount += directionChanged(newAcceleration) ? 1 : 0;
        acceleration = newAcceleration;

        //listener.notify(formSitUpOutput());
        listener.notify(formSquatOutput());
    }

    private float getTimeSinceLastUpdate(){
        float previousUpdateTime = updateTime;
        updateTime = System.nanoTime() / ((float) Math.pow(10, 9));
        return previousUpdateTime == 0 ? 0 : updateTime - previousUpdateTime;
    }

    private float getTimeSinceLastDirectionChange(){
        float currentTime = System.nanoTime() / ((float) Math.pow(10, 9));
        return currentTime - directionChangeTime;
    }

    private boolean directionChanged(Point newAcceleration){
        float timeDelta = getTimeSinceLastDirectionChange();
        if (timeDelta < TIME_THRESHOLD) return false;
        if (newAcceleration.getZ() < Z_THRESHOLD) return false;
        if (!(acceleration.getZ() > 0
                ? newAcceleration.getZ() < 0
                : newAcceleration.getZ() > 0)) return false;
        directionChangeTime += timeDelta;
        return true;
    }

    private Integer formSquatOutput(){
//        return position.toString("position") + '\n'
//                + velocity.toString("velocity") + '\n'
//                + acceleration.toString("acceleration") + '\n'
//                + "Count: " + directionChangeCount;
        return directionChangeCount;
    }

    private int countRequiredRepetitions(int difficulty){
        return difficulty * 5;
    }

    @Override
    public boolean resultReached() {
        return requiredRepetitions <= directionChangeCount;
    }

    @Override
    public int getRequiredRepetitions() {
        return requiredRepetitions;
    }
}
