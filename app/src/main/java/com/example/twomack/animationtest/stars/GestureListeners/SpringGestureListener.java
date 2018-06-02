package com.example.twomack.animationtest.stars.GestureListeners;

import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.twomack.animationtest.stars.CollisionUtil;
import com.example.twomack.animationtest.stars.Fling;
import com.example.twomack.animationtest.stars.FlingAnimationActivity;

public class SpringGestureListener extends GestureDetector.SimpleOnGestureListener {
    private ImageView imageView;
    private Fling fling;
    private CollisionUtil collisionUtil;
    private FlingAnimationActivity starActivity;

    public SpringGestureListener(ImageView imageView, Fling fling, CollisionUtil collisionUtil, FlingAnimationActivity starActivity) {
        this.imageView = imageView;
        this.fling = fling;
        this.collisionUtil = collisionUtil;
        this.starActivity = starActivity;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        SpringAnimation springAnimation = new SpringAnimation(imageView, DynamicAnimation.Y, imageView.getY() - 200);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                collisionUtil.checkForStruckWithBar(imageView, fling);
            }
        });
        springAnimation.start();

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                //what ever you do here will be done after 1 seconds delay.
                SpringAnimation springAnimation = new SpringAnimation(imageView, DynamicAnimation.Y, starActivity.getScreenHeight() - imageView.getHeight() / 2);
                springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
                springAnimation.start();
            }
        };
        handler.postDelayed(r, 200);
        return super.onDown(e);
    }
}