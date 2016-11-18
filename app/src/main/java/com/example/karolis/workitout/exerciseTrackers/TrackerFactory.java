package com.example.karolis.workitout.exerciseTrackers;

import android.hardware.SensorManager;

import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.utilities.Listener;

/**
 * Created by Dziugas on 11/16/2016.
 */

public class TrackerFactory {
    private Listener<Integer> trackerListener;
    private SensorManager sensorManager;

    public TrackerFactory(Listener<Integer> trackerListener, SensorManager sensorManager){
        this.trackerListener = trackerListener;
        this.sensorManager = sensorManager;
    }

    public Tracker CreateTracker(Exercise exercise){
        return exercise.getName().toLowerCase().contains("squat")
                ? new SquatTracker(sensorManager, trackerListener, exercise.getDifficulty())
                : new JumpTracker(sensorManager, trackerListener, exercise.getDifficulty());
    }
}
