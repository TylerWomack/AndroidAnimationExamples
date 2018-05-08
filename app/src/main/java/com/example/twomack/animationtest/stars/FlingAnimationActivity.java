package com.example.twomack.animationtest.stars;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.twomack.animationtest.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FlingAnimationActivity extends AppCompatActivity {

    float screenHeight;
    float screenWidth;
    float friction = .7f;
    private final int startingSize = 20;
    //private final int maxSize = 500;
    private final int wallsNeededToBreak = 1;
    private final int speedNeededToBreak = 1000;
    private final int barPower = 8000;

    ArrayList<Star> starArrayList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_layout);

        ImageView bar = findViewById(R.id.bar);
        final float barHeight = bar.getDrawable().getIntrinsicHeight();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y - (barHeight * 2);
        
        createNewStar(screenWidth/2, screenHeight/2, 0, 0, null);
        createNewStar(200, 400, 0, 0, null);
        createNewStar(screenWidth/3, screenHeight/3, 0 , 0, null);
        createNewStar(0, 600, 0 , 0, null);
        addRectangle();
        startStarGenerator();
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

    public void createNewStar(float x, float y, float velocityY, float velocityX, @Nullable Star oldStar){

        ConstraintLayout.LayoutParams lp;
        Star newStar = new Star(this);
        starArrayList.add(newStar);

        newStar.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        if (oldStar == null){
            lp = new ConstraintLayout.LayoutParams(startingSize, startingSize);
            if (starArrayList.size() == 1){
                lp = new ConstraintLayout.LayoutParams(startingSize * 4, startingSize * 4);
            }
        }else{
            lp = new ConstraintLayout.LayoutParams(oldStar.getHeight()/2, oldStar.getWidth()/2);
        }

        constraintLayout.addView(newStar, -1, lp);

        if (oldStar != null)
        oldStar.setLayoutParams(lp);

        newStar.setX(x);
        newStar.setY(y);

        GestureDetector newDetector = new GestureDetector(this, new FlingGestureListener(newStar));
        setGestureDetector(newDetector, newStar);

        if (velocityX != 0)
            simpleXFling(newStar, velocityX);

        if (velocityY!= 0)
            simpleYFling(newStar, velocityY);

    }

    public void startStarGenerator(){
        final Random random = new Random();

            final Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    //what ever you do here will be done after 1 seconds delay.
                    createNewStar(random.nextFloat() * screenHeight, random.nextFloat() * screenWidth, random.nextFloat() * 100, random.nextFloat() * 100, null);
                    startStarGenerator();
                }
            };
            handler.postDelayed(r, 500);
    }

    public void changeStarSize(Star star, int newSize){
        //newSize = Math.min(maxSize, newSize);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(newSize, newSize);
        constraintLayout.removeView(star);
        constraintLayout.addView(star, -1, lp);
    }

    public void addRectangle(){
        ImageView rectangle = findViewById(R.id.bar);
        GestureDetector newDetector = new GestureDetector(this, new SpringGestureListener(rectangle));
        setGestureDetector(newDetector, rectangle);
    }

    class FlingGestureListener extends GestureDetector.SimpleOnGestureListener{

        Star imageView;

        FlingGestureListener(Star imageView){
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



    public void flingX(final Star object,
                       final float velocityX){

        object.setHasHitWallAfterFling(false);
        object.setWallsHitSinceFling(0);

        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        object.setXVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        if (object.getX() < 0 && velocity < 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingX.setStartVelocity(newVelocity);
                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getXVelocity() > speedNeededToBreak){
                                createNewStar(object.getX(), object.getY(), 0, velocity, object);
                            }
                            object.setHasHitWallAfterFling(true);
                        }else if(object.getX() > screenWidth - ((object.getWidth() - object.getPaddingRight())) && velocity > 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingX.setStartVelocity(newVelocity);
                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getXVelocity() > speedNeededToBreak){
                                createNewStar(object.getX(), object.getY(), 0, velocity, object);
                            }
                            object.setHasHitWallAfterFling(true);
                        }
                    }
                })
                .start();
    }

    public void flingY(final Star object, final float velocityY){

        object.setHasHitWallAfterFling(false);
        object.setWallsHitSinceFling(0);

        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        if (!object.GetHasHitWallAfterFling())
                            checkForOverlaps(object);

                        object.setYVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        if (object.getY() < 0 && velocity < 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingY.setStartVelocity(newVelocity);
                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getYVelocity() > speedNeededToBreak){
                                createNewStar(object.getX(), object.getY(), velocity, 0, object);
                            }
                            object.setHasHitWallAfterFling(true);
                        }else if(object.getY() > screenHeight - ((object.getHeight() * 2)) && velocity > 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingY.setStartVelocity(newVelocity);
                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getYVelocity() > speedNeededToBreak){
                                createNewStar(object.getX(), object.getY(), velocity, 0, object);
                            }
                            object.setHasHitWallAfterFling(true);
                        }
                    }
                })
                .start();
    }

    public void checkForOverlaps(Star star){

        Rect rect = new Rect();
        rect.set((int) star.getX(), (int) star.getY(), (int) (star.getX() + star.getWidth()), (int) (star.getY() + star.getHeight()));

        Iterator<Star> iterator = starArrayList.iterator();
        while (iterator.hasNext()){
            Star toCheck = iterator.next();
            //we don't want to check if a star overlaps itself.
            if (toCheck != star){
                if (star.getWidth() > toCheck.getWidth()){
                    Rect check = new Rect((int) toCheck.getX(), (int) toCheck.getY(), (int) (toCheck.getX() + toCheck.getWidth()), (int) (toCheck.getY() + toCheck.getHeight()));
                    if (rect.intersect(check)){
                        changeStarSize(star, (int) (star.getWidth() * findSizeAfterConsuming(star, toCheck)));
                        toCheck.setVisibility(View.GONE);
                        iterator.remove();
                    }
                }
            }
        }
    }

    public double findSizeAfterConsuming(Star star, Star consumed){

        double totalOriginalSize = ((star.getWidth() * .25) * (star.getWidth() * .25 ))/1000;
        double totalConsumedSize = ((consumed.getWidth() * .25) * (consumed.getWidth() * .25))/1000;

        double combinedSize = totalConsumedSize + totalOriginalSize;
        return combinedSize/totalOriginalSize;
    }

    public void checkForStruckWithBar(ImageView bar){
        Rect rect = new Rect();
        rect.set((int) bar.getX(), (int)  bar.getY(), (int) (bar.getX() + bar.getWidth()), (int) (bar.getY() + bar.getHeight()));

        for (Star toCheck : starArrayList) {
            Rect check = new Rect((int) toCheck.getX(), (int) toCheck.getY(), (int) (toCheck.getX() + toCheck.getWidth()), (int) (toCheck.getY() + toCheck.getHeight()));

            if (rect.intersect(check)) {
                float newYVelocity = toCheck.getYVelocity() - barPower;
                flingY(toCheck, newYVelocity);
            }
        }
    }

    //same as a normal FlingX, except it doesn't splinter into new stars when it hits the walls.
    public void simpleXFling(final Star object, final float velocityX){
        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        object.setXVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        if (object.getX() < 0 && velocity < 0){
                            flingX.setStartVelocity(newVelocity);
                        }else if(object.getX() > screenWidth - ((object.getWidth() - object.getPaddingRight())) && velocity > 0){
                            flingX.setStartVelocity(newVelocity);
                        }
                    }
                })
                .start();
    }

    //same as a normal FlingY, except it doesn't splinter into new stars when it hits the walls.
    public void simpleYFling(final Star object, final float velocityY){
        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        object.setYVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        if (object.getY() < 0 && velocity < 0){
                            flingY.setStartVelocity(newVelocity);
                        }else if(object.getY() > screenHeight - ((object.getHeight() * 2)) && velocity > 0){
                            flingY.setStartVelocity(newVelocity);
                        }
                    }
                })
                .start();
    }

    class SpringGestureListener extends GestureDetector.SimpleOnGestureListener{
        ImageView imageView;
        SpringGestureListener(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            SpringAnimation springAnimation = new SpringAnimation(imageView, DynamicAnimation.Y, imageView.getY() - 200);
            springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                @Override
                public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                    checkForStruckWithBar(imageView);
                }
            });
            springAnimation.start();

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    //what ever you do here will be done after 1 seconds delay.
                    SpringAnimation springAnimation = new SpringAnimation(imageView, DynamicAnimation.Y, screenHeight - imageView.getHeight()/2);
                    springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
                    springAnimation.start();
                }
            };
            handler.postDelayed(r, 200);
            return super.onDown(e);
        }
    }

}