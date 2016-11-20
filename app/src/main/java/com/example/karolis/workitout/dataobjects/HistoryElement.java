package com.example.karolis.workitout.dataobjects;

import java.util.Date;

/**
 * Created by Karolis on 2016-11-20.
 */

public class HistoryElement {
    private String date;
    private String name;
    private String duration;

    public HistoryElement(String date, String name, String duration) {
        this.date = date;
        this.name = name;
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }
}
