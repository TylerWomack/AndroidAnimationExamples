package com.example.twomack.animationtest.stars;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;

import com.example.twomack.animationtest.stars.objects.Star;
import com.example.twomack.animationtest.stars.objects.StellarObject;

import static com.example.twomack.animationtest.stars.AppConstants.friction;

public class Fling {

    private FlingAnimationActivity starActivity;

    public Fling(FlingAnimationActivity starActivity){
        this.starActivity = starActivity;
    }

    //same as a normal FlingX, except it doesn't splinter into new stars when it hits the walls.
    public void simpleXFling(final Star object, final float velocityX) {
        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        object.setXVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        if (object.getX() < 0 && velocity < 0) {
                            flingX.setStartVelocity(newVelocity);
                        } else if (object.getX() > starActivity.screenWidth - ((object.getWidth() - object.getPaddingRight())) && velocity > 0) {
                            flingX.setStartVelocity(newVelocity);
                        }
                    }
                })
                .start();
    }

    //same as a normal FlingY, except it doesn't splinter into new stars when it hits the walls.
    public void simpleYFling(final Star object, final float velocityY) {
        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        object.setYVelocity(velocity);
                        float newVelocity = 0 - velocity;
                        if (object.getY() < 0 && velocity < 0) {
                            flingY.setStartVelocity(newVelocity);
                        } else if (object.getY() > starActivity.screenHeight - ((object.getHeight() * 2)) && velocity > 0) {
                            flingY.setStartVelocity(newVelocity);
                        }
                    }
                })
                .start();
    }

    public void flingX(final StellarObject object,
                       final float velocityX, final CollisionUtil collisionUtil) {

        object.setHasHitWallAfterFling(false);
        object.setWallsHitSinceFling(0);

        final FlingAnimation flingX = new FlingAnimation(object, DynamicAnimation.X);
        flingX.setStartVelocity(velocityX)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        //todo: if instanceOf()
                        if (object.getClass() == Star.class) {
                            Star star = (Star) object;
                            collisionUtil.checkForBaddieDestruction(star);
                        }

                        object.setXVelocity(velocity);
                        if (object.getX() < 0 && velocity < 0) {
                            collisionUtil.hitWall(object, flingX, velocity, false);

                        } else if (object.getX() > starActivity.screenWidth - ((object.getWidth() - object.getPaddingRight())) && velocity > 0) {
                            collisionUtil.hitWall(object, flingX, velocity, false);
                        }
                    }
                })
                .start();
    }

    public void flingY(final StellarObject object, final float velocityY, final CollisionUtil collisionUtil) {

        object.setHasHitWallAfterFling(false);
        object.setWallsHitSinceFling(0);

        final FlingAnimation flingY = new FlingAnimation(object, DynamicAnimation.Y);
        flingY.setStartVelocity(velocityY)
                .setFriction(friction)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

                        //note: I'm only checking for star consumption in flingY. Stars will not be consumed in the rare event that
                        //the user flings a star in a perfectly horizontal direction, however, this will make the program much more efficient. (Talk about adding a method to solve this)
                        if (!object.GetHasHitWallAfterFling())
                            collisionUtil.checkForStarConsumption(object);

                        //I am, however, checking for baddie destruction in both X and Y flings, as there are far fewer of them to check.
                        if (object.getClass() == Star.class) {
                            Star star = (Star) object;
                            collisionUtil.checkForBaddieDestruction(star);
                        }

                        object.setYVelocity(velocity);

                        //hit wall
                        if (object.getY() < 0 && velocity < 0) {

                            collisionUtil.hitWall(object, flingY, velocity, true);

                        } else if (object.getY() > starActivity.screenHeight - ((object.getHeight() * 2)) && velocity > 0) {

                            collisionUtil.hitWall(object, flingY, velocity, true);
                        }
                    }
                })
                .start();
    }

}
