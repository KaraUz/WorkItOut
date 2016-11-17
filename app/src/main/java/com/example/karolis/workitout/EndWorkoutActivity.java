package com.example.karolis.workitout;

import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Dziugas on 11/16/2016.
 */

public class EndWorkoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_workout);

        Intent mIntent = getIntent();
        long workoutDurationInSeconds = mIntent.getLongExtra("workoutDuration", 0);
        String asdf = mIntent.getStringExtra("workoutDuration");

        Resources res = getResources();
        ((TextView)findViewById(R.id.final_time_view)).setText(
                res.getString(R.string.your_time, formatTimeOutput(workoutDurationInSeconds)));
    }

    private String formatTimeOutput(long durationInSeconds){
        long hours = durationInSeconds / 3600;
        long minutes = durationInSeconds / 60 - hours * 60;
        long seconds = durationInSeconds % 60;

        String hoursText = hours > 0 ? hours + " hours " : "";
        String minutesText = minutes > 0 ? minutes + " minutes " : "";
        String secondsText = seconds + " seconds";

        return hoursText + minutesText + secondsText;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
