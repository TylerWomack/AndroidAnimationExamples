package com.example.twomack.animationtest.stars;

import android.content.Context;
import android.widget.ImageView;

public class Star extends android.support.v7.widget.AppCompatImageView {

    private boolean hasHitWallAfterFling;
    private float xVelocity;
    private float yVelocity;
    private int wallsHitSinceFling;

    public Star(Context context) {
        super(context);
    }

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
