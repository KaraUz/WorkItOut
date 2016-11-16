package com.example.karolis.workitout.exerciseTrackers;

/**
 * Created by Dziugas on 11/16/2016.
 */

public interface Tracker {
    void startTracking();
    void pauseTracking();
    boolean resultReached();
    int getRequiredRepetitions();
}
