package com.example.twomack.animationtest;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.Random;

public class FlingAnimationActivity extends AppCompatActivity {

    float height;
    float width;
    float friction = .7f;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_layout);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        height = size.y;

        createNewStar(0, 0, 0, 0, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setGestureDetector(final GestureDetector gestureDetector, ImageView imageView){
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void createNewStar(float x, float y, @Nullable float velocityY, @Nullable float velocityX, @Nullable ImageView oldStar){
        ImageView newStar = new ImageView(this);
        newStar.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        if (oldStar == null){
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(800, 800);
            constraintLayout.addView(newStar, -1, lp);
            newStar.setX(x);
            newStar.setY(y);
            GestureDetector newDetector = new GestureDetector(this, new MyGestureListener(newStar));
            setGestureDetector(newDetector, newStar);
            return;
        }

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(oldStar.getHeight()/2, oldStar.getWidth()/2);
        constraintLayout.addView(newStar, -1, lp);

        oldStar.setLayoutParams(lp);

        newStar.setX(x);
        newStar.setY(y);

        GestureDetector newDetector = new GestureDetector(this, new MyGestureListener(newStar));
        setGestureDetector(newDetector, newStar);

        if (velocityX != 0) {
            setInitialX(newStar, velocityX);
        }

        if (velocityY!= 0){
            setInitialY(newStar, velocityY);
        }
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        ImageView imageView;

        public MyGestureListener(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            flingX(imageView, velocityX);
            flingY(imageView, velocityY);

            return true;
        }
    }

    public void flingX(final ImageView object,
                       final float velocityX){
        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        float newVelocity = 0 - velocity;

                        if (object.getX() < 0 && velocity < 0){
                            flingX.setStartVelocity(newVelocity);
                            createNewStar(object.getX(), object.getY(), 0, velocity, object);
                        }else if(object.getX() > width - ((object.getWidth() - object.getPaddingRight())) && velocity > 0){
                            flingX.setStartVelocity(newVelocity);
                            createNewStar(object.getX(), object.getY(), 0, velocity, object);
                        }
                    }
                })
                .start();
    }

    public void flingY(final ImageView object, final float velocityY){
        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        float newVelocity = 0 - velocity;
                        if (object.getY() < 0 && velocity < 0){
                            flingY.setStartVelocity(newVelocity);
                            createNewStar(object.getX(), object.getY(), velocity, 0, object);
                        }else if(object.getY() > height - ((object.getHeight() * 2)) && velocity > 0){
                            flingY.setStartVelocity(newVelocity);
                            createNewStar(object.getX(), object.getY(), velocity, 0, object);
                        }
                    }
                })
                .start();
    }

    //same as a normal FlingX, except it doesn't splinter into new stars when it hits the walls.
    public void setInitialX(final ImageView object, final float velocityX){
        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        float newVelocity = 0 - velocity;
                        if (object.getX() < 0 && velocity < 0){
                            flingX.setStartVelocity(newVelocity);
                        }else if(object.getX() > width - ((object.getWidth() - object.getPaddingRight())) && velocity > 0){
                            flingX.setStartVelocity(newVelocity);
                        }
                    }
                })
                .start();
    }

    //same as a normal FlingY, except it doesn't splinter into new stars when it hits the walls.
    public void setInitialY(final ImageView object, final float velocityY){
        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        float newVelocity = 0 - velocity;
                        if (object.getY() < 0 && velocity < 0){
                            flingY.setStartVelocity(newVelocity);
                        }else if(object.getY() > height - ((object.getHeight() * 2)) && velocity > 0){
                            flingY.setStartVelocity(newVelocity);
                        }
                    }
                })
                .start();
    }
}