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
    ImageView star2;
    ImageView star3;
    ImageView star4;
    private GestureDetector mDetector1;
    private GestureDetector mDetector2;
    private GestureDetector mDetector3;
    private GestureDetector mDetector4;
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
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);

        mDetector1 = new GestureDetector(this, new MyGestureListener(star));
        mDetector2 = new GestureDetector(this, new MyGestureListener(star2));
        mDetector3 = new GestureDetector(this, new MyGestureListener(star3));
        mDetector4 = new GestureDetector(this, new MyGestureListener(star4));

        setGestureDetector(mDetector1, star);
        setGestureDetector(mDetector2, star2);
        setGestureDetector(mDetector3, star3);
        setGestureDetector(mDetector4, star4);
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
                       float velocityX){
        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(.5f)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        if (object.getX() < 0 && velocity < 0){
                            flingX.setStartVelocity(0 - velocity);
                        }else if(object.getX() > width - ((object.getWidth() - object.getPaddingRight())) && velocity > 0){
                            flingX.setStartVelocity(0 - velocity);
                        }
                    }
                })
                .start();
    }

    public void flingY(final ImageView object, float velocityY){
        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(.5f)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        if (object.getY() < 0 && velocity < 0){
                            flingY.setStartVelocity(0 - velocity);
                        }else if(object.getY() > height - ((object.getHeight() * 2)) && velocity > 0){
                            flingY.setStartVelocity(0 - velocity);
                        }
                    }
                })
                .start();
    }
}