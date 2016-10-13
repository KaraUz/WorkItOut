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
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

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
        point.setX(event.values[0]);
        point.setY(event.values[1]);
        point.setZ(event.values[2]);

        text.setText(point.toString());
        // text.setText("Gravity values: " +df.format(gravity[0]) + "\n" + df.format(gravity[1])+ "\n" + df.format(gravity[2])+
        //        "\nLinear acceleration: " + df.format(linear_acceleration[0]) + "\n" + df.format(linear_acceleration[1])+ "\n" + df.format(linear_acceleration[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Todo
    }
}
