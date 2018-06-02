package com.example.twomack.animationtest.stars;

import android.os.Handler;
import com.example.twomack.animationtest.stars.objects.Baddie;
import java.util.Random;
import static com.example.twomack.animationtest.stars.AppConstants.difficultyIncreaseInterval;
import static com.example.twomack.animationtest.stars.AppConstants.difficultyIncreaseLimit;
import static com.example.twomack.animationtest.stars.AppConstants.difficultyIncreaseMultiplier;

public class Generators {

    private FlingAnimationActivity starActivity;
    private Handler handler;
    private ObjectCreator objectCreator;
    private Fling fling;
    private CollisionUtil collisionUtil;

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
        handler.postDelayed(r, starActivity.getBaddieRespawnRate());
    }

    public void startBaddieMovement() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                moveAllBaddies();
                startBaddieMovement();
            }
        };
        handler.postDelayed(r, starActivity.getBaddieMovementDelay());
    }

    public void startStarGenerator() {
        final Random random = new Random();
        final Runnable r = new Runnable() {
            public void run() {
                objectCreator.createNewStar(random.nextFloat() * starActivity.getScreenHeight(), random.nextFloat() * starActivity.getScreenWidth(), random.nextFloat() * 100, random.nextFloat() * 100, null);
                startStarGenerator();
            }
        };
        handler.postDelayed(r, starActivity.getStarGenerationDelay());
    }

    public void startDifficultyGenerator() {
        final Runnable r = new Runnable() {
            public void run() {
                increaseDifficulty();

                if (starActivity.getTimesDifficultyIncreased() > difficultyIncreaseLimit) {
                    return;
                }

                starActivity.setTimesDifficultyIncreased(starActivity.getTimesDifficultyIncreased() + 1);
                startDifficultyGenerator();
            }
        };
        handler.postDelayed(r, difficultyIncreaseInterval);
    }

    private void moveAllBaddies() {
        for (Baddie baddie : starActivity.baddieArrayList) {
            fling.flingY(baddie, baddie.getVerticalFlingSpeed(), collisionUtil);
            fling.flingX(baddie, baddie.getHorizontalFlingSpeed(), collisionUtil);
        }
    }

    private void increaseDifficulty() {
        starActivity.setBaddieSpeed((int) (starActivity.getBaddieSpeed() * difficultyIncreaseMultiplier));
        starActivity.setBaddieMovementDelay((int) (starActivity.getBaddieMovementDelay() / difficultyIncreaseMultiplier));
        starActivity.setBaddieRespawnRate((int) (starActivity.getBaddieRespawnRate() / difficultyIncreaseMultiplier));
        starActivity.setStarGenerationDelay((int) ((double) (starActivity.getStarGenerationDelay() / difficultyIncreaseMultiplier)));
        }
}
