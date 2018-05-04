package com.example.twomack.animationtest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class FlingAnimationActivity extends AppCompatActivity {

    ImageView star;
    private GestureDetector mDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_layout);

        star = findViewById(R.id.star);
        // get the gesture detector
        mDetector = new GestureDetector(this, new MyGestureListener());
        star.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            FlingAnimation flingX = new FlingAnimation(star, DynamicAnimation.SCROLL_X);
            flingX.setStartVelocity(-velocityX)
                    .setFriction(5f)
                    .start();

            FlingAnimation flingY = new FlingAnimation(star, DynamicAnimation.SCROLL_Y);
            flingY.setStartVelocity(-velocityY)
                    .setFriction(5f)
                    .start();
            return true;
        }
}
}