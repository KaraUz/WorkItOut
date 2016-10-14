package com.example.karolis.workitout.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.karolis.workitout.dataobjects.Point;
import com.example.karolis.workitout.utilities.Listener;

import java.util.Calendar;

/**
 * Created by Dziugas on 10/14/2016.
 */

public class Accelerometer implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float lastUpdateTime;
    private Point point;
    private Listener<String> listener;

    public Accelerometer(SensorManager mSensorManager, Listener<String> listener){
        this.mSensorManager = mSensorManager;
        this.mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.listener = listener;

        point = new Point();
        lastUpdateTime = Calendar.getInstance().getTimeInMillis();
    }

    public void startTracking(){
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pauseTracking(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        point.setX(event.values[0]);
        point.setY(event.values[1]);
        point.setZ(event.values[2]);

        listener.notify(point.toString());
            // text.setText("Gravity values: " +df.format(gravity[0]) + "\n" + df.format(gravity[1])+ "\n" + df.format(gravity[2])+
            //        "\nLinear acceleration: " + df.format(linear_acceleration[0]) + "\n" + df.format(linear_acceleration[1])+ "\n" + df.format(linear_acceleration[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Todo
    }
}
