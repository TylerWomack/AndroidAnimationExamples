package com.example.twomack.animationtest;

import android.annotation.SuppressLint;
import android.graphics.Point;
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
    float height;
    float width;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_layout);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        height = size.y;

        star = findViewById(R.id.star);
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

            final FlingAnimation flingX = new FlingAnimation(star, DynamicAnimation.X);
            flingX.setStartVelocity(velocityX)
                    .setFriction(.5f)
                    .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                        @Override
                        public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                                if (star.getX() < 0 && velocity < 0){
                                    flingX.setStartVelocity(0 - velocity);
                                }else if(star.getX() > width - ((star.getWidth() - star.getPaddingRight())) && velocity > 0){
                                    flingX.setStartVelocity(0 - velocity);
                                }
                                flingX.start();
                        }
                    })
                    .start();

            final FlingAnimation flingY = new FlingAnimation(star, DynamicAnimation.Y);
            flingY.setStartVelocity(velocityY)
                    .setFriction(.5f)
                    .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                        @Override
                        public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                                if (star.getY() < 0 && velocity < 0){
                                    flingY.setStartVelocity(0 - velocity);
                                }else if(star.getY() > height - ((star.getHeight() * 2)) && velocity > 0){
                                    flingY.setStartVelocity(0 - velocity);
                                }
                                flingY.start();
                        }
                    })
                    .start();
            return true;
        }
    }
}