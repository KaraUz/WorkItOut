package com.example.karolis.workitout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.karolis.workitout.dataobjects.Point;

import java.util.Calendar;

public class ExerciseActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView text;
    private Point point;
    private float lastUpdateTime;
    private Point gravity = null;
    private Point velocity = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        text = (TextView)findViewById(R.id.text_field);
        text.setText("event not triggered");

        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        point = new Point();
        lastUpdateTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public void onSensorChanged(SensorEvent event){
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        Point acceleration = new Point();
        float timeTmp = Calendar.getInstance().getTimeInMillis();
        float deltaTime = timeTmp - lastUpdateTime;
        lastUpdateTime = timeTmp;
        /*final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];*/

        if(gravity == null){
            gravity = new Point();
            gravity.setX(event.values[0]);
            gravity.setY(event.values[1]);
            gravity.setZ(event.values[2]);
        }

        acceleration.setX(event.values[0]);
        acceleration.setY(event.values[1]);
        acceleration.setZ(event.values[2]);

        acceleration.sub(gravity);
        //acceleration.times(deltaTime);
        acceleration.sub(velocity);
        velocity = acceleration;

        point = velocity;//.times(deltaTime);
        text.setText(point.toString());
        // text.setText("Gravity values: " +df.format(gravity[0]) + "\n" + df.format(gravity[1])+ "\n" + df.format(gravity[2])+
        //        "\nLinear acceleration: " + df.format(linear_acceleration[0]) + "\n" + df.format(linear_acceleration[1])+ "\n" + df.format(linear_acceleration[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Todo
    }
}
