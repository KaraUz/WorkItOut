package com.example.karolis.workitout.exerciseTrackers;

/**
 * Created by Dziugas on 11/16/2016.
 */

public class IdleTracker implements Tracker {
    @Override
    public void startTracking() {
    }

    @Override
    public void pauseTracking() {
    }

    @Override
    public boolean resultReached(){
        return false;
    }

    @Override
    public int getRequiredRepetitions() {
        return 0;
    }
}
