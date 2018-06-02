package com.example.twomack.animationtest.stars;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.twomack.animationtest.R;
import com.example.twomack.animationtest.stars.objects.Baddie;
import com.example.twomack.animationtest.stars.objects.Star;

import java.util.ArrayList;

public class FlingAnimationActivity extends AppCompatActivity {


    private float screenHeight;
    private float screenWidth;
    private int baddieSpeed = 500;
    private int baddieMovementDelay = 1500;
    private int baddieRespawnRate = 10000;
    private int starGenerationDelay = 200;
    private int timesDifficultyIncreased = 0;

    //region getters and setters
    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public int getBaddieSpeed() {
        return baddieSpeed;
    }

    public void setBaddieSpeed(int baddieSpeed) {
        this.baddieSpeed = baddieSpeed;
    }

    public int getBaddieMovementDelay() {
        return baddieMovementDelay;
    }

    public void setBaddieMovementDelay(int baddieMovementDelay) {
        this.baddieMovementDelay = baddieMovementDelay;
    }

    public int getBaddieRespawnRate() {
        return baddieRespawnRate;
    }

    public void setBaddieRespawnRate(int baddieRespawnRate) {
        this.baddieRespawnRate = baddieRespawnRate;
    }

    public int getStarGenerationDelay() {
        return starGenerationDelay;
    }

    public void setStarGenerationDelay(int starGenerationDelay) {
        this.starGenerationDelay = starGenerationDelay;
    }

    public int getTimesDifficultyIncreased() {
        return timesDifficultyIncreased;
    }

    public void setTimesDifficultyIncreased(int timesDifficultyIncreased) {
        this.timesDifficultyIncreased = timesDifficultyIncreased;
    }

    public CollisionUtil getCollisionUtil() {
        return collisionUtil;
    }

    public ConstraintLayout getMainLayout() {
        return mainLayout;
    }

    public ArrayList<Star> getStarArrayList() {
        return starArrayList;
    }

    public ArrayList<Baddie> getBaddieArrayList() {
        return baddieArrayList;
    }
    //endregion

    CollisionUtil collisionUtil;
    Fling fling;
    ObjectCreator objectCreator;
    ConstraintLayout mainLayout;
    ArrayList<Star> starArrayList = new ArrayList<>();
    ArrayList<Baddie> baddieArrayList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_layout);

        mainLayout = findViewById(R.id.constraintLayout);

        //dependency injection would be great here
        fling = new Fling(this);
        objectCreator = new ObjectCreator(this, fling);
        collisionUtil = new CollisionUtil(this, objectCreator);
        Generators generator = new Generators(this, objectCreator, fling, collisionUtil);

        findBorder();
        objectCreator.createInitialObjects();
        generator.startBaddieGenerator();
        generator.startStarGenerator();
        generator.startDifficultyGenerator();
        generator.startBaddieMovement();
    }

    public void findBorder(){
        ImageView bar = findViewById(R.id.bar);
        final float barHeight = bar.getDrawable().getIntrinsicHeight();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y - (barHeight * 2);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}