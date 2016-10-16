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

    public Point sub(Point point){
        return new Point(x - point.getX(), y - point.getY(), z - point.getZ());
    }

    public Point add(Point point){
        return new Point(x + point.getX(), y + point.getY(), z + point.getZ());
    }

    public Point times(float mult){
        return new Point(x * mult, y * mult, z * mult);
    }

    @Override
    public String toString(){
        return toString("Point");
    }

    public String toString(String vectorName) {

        DecimalFormat df = new DecimalFormat("#.#");
        return vectorName + "{ " +
                "x=" + df.format(x) +
                ", y=" + df.format(y) +
                ", z=" + df.format(z) +
                '}';
    }
}
