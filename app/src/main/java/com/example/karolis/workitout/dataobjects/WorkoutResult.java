package com.example.karolis.workitout.dataobjects;

import android.content.ContentValues;

/**
 * Created by Dziugas on 11/17/2016.
 */

public class WorkoutResult {
    private String workoutName;
    private String date;
    private String durationText;
    private long durationInSeconds;

    public WorkoutResult(String workoutName, String date, long durationInSeconds){
        this.workoutName = workoutName;
        this.date = date;
        this.durationText = formatDurationText(durationInSeconds);
        this.durationInSeconds = durationInSeconds;
    }

    private String formatDurationText(long durationInSeconds){
        long hours = durationInSeconds / 3600;
        long minutes = durationInSeconds / 60 - hours * 60;
        long seconds = durationInSeconds % 60;

        String hoursPrefix = hours < 9 ? "0" : "";
        String hoursText = hours + ":";
        String minutesPrefix = minutes < 9 ? "0" : "";
        String minutesText = minutes + ":";
        String secondsPrefix = seconds < 9 ? "0" : "";
        String secondsText = String.valueOf(seconds);

        return hoursPrefix + hoursText + minutesPrefix + minutesText + secondsPrefix + secondsText;
    }

    public ContentValues toContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("WorkoutName", workoutName);
        contentValues.put("WorkoutDate", date);
        contentValues.put("DurationText", durationText);
        contentValues.put("DurationInSeconds", durationInSeconds);
        return contentValues;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getDate() {
        return date;
    }

    public String getDurationText() {
        return durationText;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }
}
