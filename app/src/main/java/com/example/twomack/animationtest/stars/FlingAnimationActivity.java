package com.example.twomack.animationtest.stars;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.twomack.animationtest.R;
import com.example.twomack.animationtest.stars.objects.Baddie;
import com.example.twomack.animationtest.stars.objects.Star;

import java.util.ArrayList;

public class FlingAnimationActivity extends AppCompatActivity {

    public float screenHeight;
    public float screenWidth;
    public int baddieSpeed = 500;
    public int baddieMovementDelay = 1500;
    public int baddieRespawnRate = 10000;
    public int starGenerationDelay = 200;
    public int timesDifficultyIncreased = 0;

    CollisionUtil collisionUtil;
    Fling fling;
    ObjectCreator objectCreator;

    //todo: figure out how to use this.
    //AppConstants appConstants;

    //mention dependency injection as a good idea here

    ArrayList<Star> starArrayList = new ArrayList<>();
    ArrayList<Baddie> baddieArrayList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_layout);

        //display metrics - you're currently using pixels and not dp
        //DisplayMetrics metrics = getResources().getDisplayMetrics();
        //metrics.density
        //test on different densities
        //if you're low density, do x, otherwise...
        //you can bitmaps, etc dynamically based on density of the screen.

        //perfect dependency injection use case
        fling = new Fling(this);
        objectCreator = new ObjectCreator(this, fling);
        collisionUtil = new CollisionUtil(this, objectCreator);
        Generators generator = new Generators(this, objectCreator, fling, collisionUtil);

        findScreenDimens();
        objectCreator.createInitialObjects();
        generator.startBaddieGenerator();
        generator.startStarGenerator();
        generator.startDifficultyGenerator();
        generator.startBaddieMovement();

    }

    public void findScreenDimens(){
        ImageView bar = findViewById(R.id.bar);
        final float barHeight = bar.getDrawable().getIntrinsicHeight();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y - (barHeight * 2);
    }

    public CollisionUtil getCollisionUtil() {
        return collisionUtil;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}