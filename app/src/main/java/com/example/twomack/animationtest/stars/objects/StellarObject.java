package com.example.twomack.animationtest.stars.objects;

import android.content.Context;
import android.widget.ImageView;

public abstract class StellarObject extends android.support.v7.widget.AppCompatImageView{
    public StellarObject(Context context) {
        super(context);
    }

    private boolean hasHitWallAfterFling;
    private int wallsHitSinceFling;
    private float xVelocity;
    private float yVelocity;

    public int getWallsHitSinceFling() {
        return wallsHitSinceFling;
    }

    public void setWallsHitSinceFling(int wallsHitSinceFling) {
        this.wallsHitSinceFling = wallsHitSinceFling;
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
