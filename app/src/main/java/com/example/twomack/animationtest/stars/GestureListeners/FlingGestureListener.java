package com.example.twomack.animationtest.stars.GestureListeners;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.twomack.animationtest.stars.CollisionUtil;
import com.example.twomack.animationtest.stars.Fling;
import com.example.twomack.animationtest.stars.objects.Star;

public class FlingGestureListener extends GestureDetector.SimpleOnGestureListener {

    Star imageView;
    Fling fling;
    CollisionUtil collisionUtil;

    FlingGestureListener(Star imageView, Fling fling, CollisionUtil collisionUtil) {
        this.imageView = imageView;
        this.fling = fling;
        this.collisionUtil = collisionUtil;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        fling.flingX(imageView, velocityX, collisionUtil);
        fling.flingY(imageView, velocityY, collisionUtil);
        return true;
    }
}