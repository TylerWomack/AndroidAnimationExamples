package com.example.twomack.animationtest.stars.objects;

import android.content.Context;

import com.example.twomack.animationtest.stars.objects.StellarObject;

public class Baddie extends StellarObject {


    private boolean hasHitWallAfterFling;
    private float xVelocity;
    private float yVelocity;
    private int wallsHitSinceFling;


    private int flingXVelocity;
    private int flingYVelocity;


    public Baddie(Context context) {
        super(context);
    }

    public int getWallsHitSinceFling() {
        return wallsHitSinceFling;
    }

    public void setWallsHitSinceFling(int wallsHitSinceFling) {
        this.wallsHitSinceFling = wallsHitSinceFling;
    }


    public int getFlingXVelocity() {
        return flingXVelocity;
    }

    public void setFlingXVelocity(int flingXVelocity) {
        this.flingXVelocity = flingXVelocity;
    }

    public int getFlingYVelocity() {
        return flingYVelocity;
    }

    public void setFlingYVelocity(int flingYVelocity) {
        this.flingYVelocity = flingYVelocity;
    }

    public float getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public boolean GetHasHitWallAfterFling() {
        return hasHitWallAfterFling;
    }

    public void setHasHitWallAfterFling(boolean hasHitWallAfterFling) {
        this.hasHitWallAfterFling = hasHitWallAfterFling;
    }

}
