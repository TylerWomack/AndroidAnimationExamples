package com.example.twomack.animationtest.stars;

import android.app.Activity;
import android.os.Handler;

import com.example.twomack.animationtest.stars.objects.Baddie;

import java.util.Random;

import static com.example.twomack.animationtest.stars.AppConstants.difficultyIncreaseInterval;
import static com.example.twomack.animationtest.stars.AppConstants.difficultyIncreaseLimit;
import static com.example.twomack.animationtest.stars.AppConstants.difficultyIncreaseMultiplier;

public class Generators {

    private FlingAnimationActivity starActivity;
    private Handler handler;
    ObjectCreator objectCreator;
    Fling fling;
    CollisionUtil collisionUtil;

    Generators(FlingAnimationActivity starActivity, ObjectCreator objectCreator, Fling fling, CollisionUtil collisionUtil){
        this.starActivity = starActivity;
        this.objectCreator = objectCreator;
        this.fling = fling;
        this.collisionUtil = collisionUtil;
        handler = new Handler();
    }

    public void startBaddieGenerator() {
        final Runnable r = new Runnable() {
            public void run() {
                objectCreator.createBaddie();
                startBaddieGenerator();
            }
        };
        handler.postDelayed(r, starActivity.baddieRespawnRate);
    }

    public void startBaddieMovement() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                moveAllBaddies();
                startBaddieMovement();
            }
        };
        handler.postDelayed(r, starActivity.baddieMovementDelay);
    }

    public void startStarGenerator() {
        final Random random = new Random();
        final Runnable r = new Runnable() {
            public void run() {
                objectCreator.createNewStar(random.nextFloat() * starActivity.screenHeight, random.nextFloat() * starActivity.screenWidth, random.nextFloat() * 100, random.nextFloat() * 100, null);
                startStarGenerator();
            }
        };
        handler.postDelayed(r, starActivity.starGenerationDelay);
    }

    public void startDifficultyGenerator() {
        final Runnable r = new Runnable() {
            public void run() {
                increaseDifficulty();

                if (starActivity.timesDifficultyIncreased > difficultyIncreaseLimit) {
                    return;
                }

                starActivity.timesDifficultyIncreased++;
                startDifficultyGenerator();
            }
        };
        handler.postDelayed(r, difficultyIncreaseInterval);
    }

    public void moveAllBaddies() {
        for (Baddie baddie : starActivity.baddieArrayList) {
            fling.flingY(baddie, baddie.getFlingYVelocity(), collisionUtil);
            fling.flingX(baddie, baddie.getFlingXVelocity(), collisionUtil);
        }
    }

    public void increaseDifficulty() {
        starActivity.baddieSpeed = (int) (starActivity.baddieSpeed * difficultyIncreaseMultiplier);
        starActivity.baddieMovementDelay = (int) (starActivity.baddieMovementDelay / difficultyIncreaseMultiplier);
        starActivity.baddieRespawnRate = (int) (starActivity.baddieRespawnRate / difficultyIncreaseMultiplier);
        starActivity.starGenerationDelay = (int) ((double) starActivity.starGenerationDelay / difficultyIncreaseMultiplier);
    }

}
