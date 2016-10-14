package com.example.karolis.workitout.dataobjects;

import java.text.DecimalFormat;

/**
 * Created by Karolis on 2016-10-10.
 */

public class Point {
    private float x = 0f;
    private float y = 0f;
    private float z = 0f;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Point(){

    }

    public Point(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void sub(Point point){
        this.x -= point.getX();
        this.y -= point.getY();
        this.z -= point.getZ();
    }

    public void add(Point point){
        this.x+=point.getX();
        this.y+=point.getY();
        this.z+=point.getZ();
    }

    public Point times(float x){
        this.x*=x;
        this.y*=x;
        this.z*=x;
        return this;
    }

    public void sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    @Override
    public String toString() {

        DecimalFormat df = new DecimalFormat("#.#");
        return "Point{" +
                "x=" + df.format(x) +
                ", y=" + df.format(y) +
                ", z=" + df.format(z) +
                '}';
    }
}
