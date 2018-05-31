package com.example.twomack.animationtest.stars;

public class AppConstants {

    public static final float friction = .7f;
    public static final int startingStarSize = 20;
    public static final int wallsNeededToBreak = 0;
    public static final int speedNeededToBreak = 1000;
    public static final int barPower = 10000;
    public static final double difficultyIncreaseMultiplier = 1.04;
    public static final int difficultyIncreaseInterval = 10000;
    public static final int baddieStartingSizeMultiplier = 3;
    public static final int difficultyIncreaseLimit = 30;

    public int baddieSpeed = 500;
    public int baddieMovementDelay = 1500;
    public int baddieRespawnRate = 10000;
    public int starGenerationDelay = 200;
    public int timesDifficultyIncreased = 0;


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



    //todo: do a couple to show I know how to do it

}
