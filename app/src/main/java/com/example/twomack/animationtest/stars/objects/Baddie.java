package com.example.twomack.animationtest.stars.objects;

import android.content.Context;

public class Baddie extends StellarObject {

    private int horizontalFlingSpeed;
    private int verticalFlingSpeed;

    public Baddie(Context context) {
        super(context);
    }

    public int getHorizontalFlingSpeed() {
        return horizontalFlingSpeed;
    }

    public void setHorizontalFlingSpeed(int horizontalFlingSpeed) {
        this.horizontalFlingSpeed = horizontalFlingSpeed;
    }

    public int getVerticalFlingSpeed() {
        return verticalFlingSpeed;
    }

    public void setVerticalFlingSpeed(int verticalFlingSpeed) {
        this.verticalFlingSpeed = verticalFlingSpeed;
    }

}
