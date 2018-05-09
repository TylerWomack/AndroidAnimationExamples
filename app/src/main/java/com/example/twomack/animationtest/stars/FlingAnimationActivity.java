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
    private final int wallsNeededToBreak = 0;
    private final int speedNeededToBreak = 1000;
    private final int barPower = 10000;
    private int baddieSpeed = 500;
    private int baddieMovementDelay = 2000;
    private int baddieRespawnRate = 10000;
    private int starGenerationDelay = 200;
    private final double difficultyIncreaseMultiplier = 1.1;
    private final int difficultyIncreaseInterval = 20000;

    ArrayList<Star> starArrayList = new ArrayList<>();
    ArrayList<Baddie> baddieArrayList = new ArrayList<>();

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
        createBaddie();
        startBaddieMovement();
        startBaddieGenerator();
        startDifficultyGenerator();
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

    public void createBaddie(){
        Baddie baddie = new Baddie(this);

        Random random = new Random();
        if(random.nextBoolean()){
            baddie.setFlingXVelocity(baddieSpeed);
        }else {
            baddie.setFlingXVelocity(0 - baddieSpeed);
        }

        if(random.nextBoolean()){
            baddie.setFlingYVelocity(baddieSpeed);
        }else {
            baddie.setFlingYVelocity(0 - baddieSpeed);
        }

        baddieArrayList.add(baddie);
        baddie.setImageDrawable(getResources().getDrawable(android.R.drawable.radiobutton_on_background));
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(startingSize * 2, startingSize * 2);
        constraintLayout.addView(baddie, -1, lp);
        baddie.setColorFilter(Color.BLACK);

        baddie.setY((float) random.nextInt((int) screenHeight));

    }

    public void moveAllBaddies(){
        for (Baddie baddie : baddieArrayList){
            flingY(baddie, baddie.getFlingYVelocity());
            flingX(baddie, baddie.getFlingXVelocity());
        }
    }

    public void increaseDifficulty(){
        baddieSpeed = (int) (baddieSpeed * difficultyIncreaseMultiplier);
        baddieMovementDelay = (int) (baddieMovementDelay / difficultyIncreaseMultiplier);
        baddieRespawnRate = (int) (baddieRespawnRate / difficultyIncreaseMultiplier);
        //starGenerationDelay = (int) ((double) starGenerationDelay/difficultyIncreaseMultiplier);
        int x = 0;
    }

    public void startDifficultyGenerator(){
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                increaseDifficulty();
                startDifficultyGenerator();
            }
        };
        handler.postDelayed(r, difficultyIncreaseInterval);
    }

    public void startBaddieGenerator(){
        final Random random = new Random();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                createBaddie();
                startBaddieGenerator();
            }
        };
        handler.postDelayed(r, baddieRespawnRate);
    }

    public void startBaddieMovement(){
        final Random random = new Random();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                moveAllBaddies();
                startBaddieMovement();
            }
        };
        handler.postDelayed(r, baddieMovementDelay);
    }

    public void createNewStar(float x, float y, float velocityY, float velocityX, @Nullable Star oldStar){

        ConstraintLayout.LayoutParams lp;
        Star newStar = new Star(this);
        starArrayList.add(newStar);


        newStar.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));

        Random random = new Random();
        if (random.nextInt(10) > 6){
            newStar.setColorFilter(getRandomColor());
        }

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        if (oldStar == null){
            lp = new ConstraintLayout.LayoutParams(startingSize, startingSize);
            if (starArrayList.size() == 1){
                lp = new ConstraintLayout.LayoutParams(startingSize * 3, startingSize * 3);
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

    public int getRandomColor(){

        Random random = new Random();
        int randomNumber = random.nextInt(7);

        switch (randomNumber){
            case 0:
                return getResources().getColor(R.color.darkblue);
            case 1:
                return getResources().getColor(R.color.green);
            case 2:
                return getResources().getColor(R.color.darkblue);
            case 3:
                return getResources().getColor(R.color.orange);
            case 4:
                return getResources().getColor(R.color.purple);
            case 5:
                return getResources().getColor(R.color.red);
            case 6:
                return getResources().getColor(R.color.yellow);
        }
        return getResources().getColor(R.color.lightblue);
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
            handler.postDelayed(r, starGenerationDelay);
    }

    public void changeStarSize(ImageView star, int newSize){
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

    public void flingX(final StellarObject object,
                       final float velocityX){

        object.setHasHitWallAfterFling(false);
        object.setWallsHitSinceFling(0);

        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        if (object.getClass() == Star.class){
                            Star star = (Star) object;
                            checkForBaddieDestruction(star);
                        }

                        object.setXVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        //hit wall
                        if (object.getX() < 0 && velocity < 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingX.setStartVelocity(newVelocity);

                            if (object.getClass() == Baddie.class){
                                Baddie baddie = (Baddie) object;
                                baddie.setFlingXVelocity(0 - baddie.getFlingXVelocity());
                            }

                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getXVelocity() > speedNeededToBreak){
                                if (object.getClass() == Star.class){
                                    createNewStar(object.getX(), object.getY(), 0, velocity, (Star) object);
                                }
                            }
                            object.setHasHitWallAfterFling(true);
                            //hit wall
                        }else if(object.getX() > screenWidth - ((object.getWidth() - object.getPaddingRight())) && velocity > 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingX.setStartVelocity(newVelocity);

                            if (object.getClass() == Baddie.class){
                                Baddie baddie = (Baddie) object;
                                baddie.setFlingXVelocity(0 - baddie.getFlingXVelocity());
                            }

                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getXVelocity() > speedNeededToBreak){
                                if (object.getClass() == Star.class){
                                    createNewStar(object.getX(), object.getY(), 0, velocity, (Star) object);
                                }
                            }
                            object.setHasHitWallAfterFling(true);
                        }
                    }
                })
                .start();
    }

    public void flingY(final StellarObject object, final float velocityY){

        object.setHasHitWallAfterFling(false);
        object.setWallsHitSinceFling(0);

        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        //note: I'm only checking for star consumption in flingY. Stars will not be consumed in the rare event that
                        //the user flings a star in a perfectly horizontal direction, however, this will make the program much more efficient.
                        if (!object.GetHasHitWallAfterFling())
                            checkForStarConsumption(object);

                        if (object.getClass() == Star.class){
                            Star star = (Star) object;
                            checkForBaddieDestruction(star);
                        }




                        object.setYVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        //hit wall
                        if (object.getY() < 0 && velocity < 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingY.setStartVelocity(newVelocity);

                            if (object.getClass() == Baddie.class){
                                Baddie baddie = (Baddie) object;
                                baddie.setFlingYVelocity(0 - baddie.getFlingYVelocity());
                            }

                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getYVelocity() > speedNeededToBreak){
                                if (object.getClass() == Star.class){
                                    createNewStar(object.getX(), object.getY(), velocity, 0, (Star) object);
                                }
                            }
                            object.setHasHitWallAfterFling(true);
                            //hit wall
                        }else if(object.getY() > screenHeight - ((object.getHeight() * 2)) && velocity > 0){
                            object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
                            flingY.setStartVelocity(newVelocity);

                            if (object.getClass() == Baddie.class){
                                Baddie baddie = (Baddie) object;
                                baddie.setFlingYVelocity(0 - baddie.getFlingYVelocity());
                            }

                            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getYVelocity() > speedNeededToBreak){
                                if (object.getClass() == Star.class){
                                    createNewStar(object.getX(), object.getY(), velocity, 0, (Star) object);

                                }
                            }
                            object.setHasHitWallAfterFling(true);
                        }
                    }
                })
                .start();
    }

    public void checkForStarConsumption(ImageView star){

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

    public void checkForBaddieDestruction(Star star){

        int starForce = getForce(star);

        Rect rect = new Rect();
        rect.set((int) star.getX(), (int) star.getY(), (int) (star.getX() + star.getWidth()), (int) (star.getY() + star.getHeight()));

        Iterator<Baddie> iterator = baddieArrayList.iterator();
        while (iterator.hasNext()){
            Baddie toCheck = iterator.next();
                    Rect check = new Rect((int) toCheck.getX(), (int) toCheck.getY(), (int) (toCheck.getX() + toCheck.getWidth()), (int) (toCheck.getY() + toCheck.getHeight()));
                    if (rect.intersect(check)){

                        if (starForce > getForce(toCheck)){
                            //todo: destroy baddie - make it more interesting. Perhaps shrink the star in question.
                            changeStarSize(star, (int) (star.getWidth() / findSizeAfterConsuming(star, toCheck)));
                            toCheck.setVisibility(View.GONE);
                            iterator.remove();
                        }
                    }
        }
    }

    public int getForce(StellarObject object){
        int accelerationProxy = (int) Math.max(object.getXVelocity(), object.getYVelocity());
        int massProxy = object.getWidth();

        return massProxy * accelerationProxy;
    }

    public double findSizeAfterConsuming(ImageView consumer, ImageView consumed){

        double totalOriginalSize = ((consumer.getWidth() * .25) * (consumer.getWidth() * .25 ))/1000;
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