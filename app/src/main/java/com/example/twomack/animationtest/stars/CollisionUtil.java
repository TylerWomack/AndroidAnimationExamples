package com.example.twomack.animationtest.stars;

import android.graphics.Rect;
import android.support.animation.FlingAnimation;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.example.twomack.animationtest.R;
import com.example.twomack.animationtest.stars.objects.Baddie;
import com.example.twomack.animationtest.stars.objects.Star;
import com.example.twomack.animationtest.stars.objects.StellarObject;

import java.util.Iterator;

import static com.example.twomack.animationtest.stars.AppConstants.barPower;
import static com.example.twomack.animationtest.stars.AppConstants.speedNeededToBreak;
import static com.example.twomack.animationtest.stars.AppConstants.wallsNeededToBreak;

public class CollisionUtil {

    FlingAnimationActivity starActivity;
    ObjectCreator objectCreator;

    public CollisionUtil(FlingAnimationActivity starActivity, ObjectCreator objectCreator){
        this.starActivity = starActivity;
        this.objectCreator = objectCreator;
    }

    public void checkForStruckWithBar(ImageView bar, Fling fling) {
        Rect rect = new Rect();
        rect.set((int) bar.getX(), (int) bar.getY(), (int) (bar.getX() + bar.getWidth()), (int) (bar.getY() + bar.getHeight()));

        for (Star toCheck : starActivity.starArrayList) {
            Rect check = new Rect((int) toCheck.getX(), (int) toCheck.getY(), (int) (toCheck.getX() + toCheck.getWidth()), (int) (toCheck.getY() + toCheck.getHeight()));

            if (rect.intersect(check)) {
                float newYVelocity = toCheck.getYVelocity() - barPower;
                fling.flingY(toCheck, newYVelocity, this);
            }
        }

        for (Baddie baddie : starActivity.baddieArrayList) {
            Rect check = new Rect((int) baddie.getX(), (int) baddie.getY(), (int) (baddie.getX() + baddie.getWidth()), (int) (baddie.getY() + baddie.getHeight()));

            if (rect.intersect(check)) {
                float newYVelocity = baddie.getYVelocity() - barPower;
                fling.flingY(baddie, newYVelocity, this);
            }
        }
    }

    public void checkForStarConsumption(ImageView star) {

        Rect rect = new Rect();
        rect.set((int) star.getX(), (int) star.getY(), (int) (star.getX() + star.getWidth()), (int) (star.getY() + star.getHeight()));

        Iterator<Star> iterator = starActivity.starArrayList.iterator();
        while (iterator.hasNext()) {
            Star toCheck = iterator.next();
            //we don't want to check if a star overlaps itself.
            if (toCheck != star) {
                if (star.getWidth() > toCheck.getWidth()) {
                    Rect check = new Rect((int) toCheck.getX(), (int) toCheck.getY(), (int) (toCheck.getX() + toCheck.getWidth()), (int) (toCheck.getY() + toCheck.getHeight()));
                    if (rect.intersect(check)) {
                        changeStarSize(star, (int) (star.getWidth() * findSizeAfterConsuming(star, toCheck)));
                        toCheck.setVisibility(View.GONE);
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void hitWall(StellarObject object, FlingAnimation fling, float oldVelocity, boolean verticalStrike) {
        object.setWallsHitSinceFling(object.getWallsHitSinceFling() + 1);
        fling.setStartVelocity(0 - oldVelocity);
        object.setHasHitWallAfterFling(true);

        if (verticalStrike) {
            if (object.getClass() == Baddie.class) {
                Baddie baddie = (Baddie) object;
                baddie.setFlingYVelocity(0 - baddie.getFlingYVelocity());
            }

            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getYVelocity() > speedNeededToBreak) {
                if (object.getClass() == Star.class) {
                    objectCreator.createNewStar(object.getX(), object.getY(), oldVelocity, 0, (Star) object);
                }
            }
        } else if (!verticalStrike) {
            if (object.getClass() == Baddie.class) {
                Baddie baddie = (Baddie) object;
                baddie.setFlingXVelocity(0 - baddie.getFlingXVelocity());
            }

            if (object.getWallsHitSinceFling() > wallsNeededToBreak && object.getXVelocity() > speedNeededToBreak) {
                if (object.getClass() == Star.class) {
                    objectCreator.createNewStar(object.getX(), object.getY(), 0, oldVelocity, (Star) object);
                }
            }
        }
    }

    public int getForce(StellarObject object) {
        int accelerationProxy = (int) Math.max(object.getXVelocity(), object.getYVelocity());
        int massProxy = object.getWidth();

        return massProxy * accelerationProxy;
    }

    public void checkForBaddieDestruction(Star star) {

        int starForce = getForce(star);
        Rect rect = new Rect();
        rect.set((int) star.getX(), (int) star.getY(), (int) (star.getX() + star.getWidth()), (int) (star.getY() + star.getHeight()));

        Iterator<Baddie> iterator = starActivity.baddieArrayList.iterator();
        while (iterator.hasNext()) {
            Baddie toCheck = iterator.next();
            Rect check = new Rect((int) toCheck.getX(), (int) toCheck.getY(), (int) (toCheck.getX() + toCheck.getWidth()), (int) (toCheck.getY() + toCheck.getHeight()));
            if (rect.intersect(check)) {

                if (starForce > getForce(toCheck)) {
                    changeStarSize(star, (int) (star.getWidth() / findSizeAfterConsuming(star, toCheck)));
                    toCheck.setVisibility(View.GONE);
                    iterator.remove();
                    //todo: get a reference to your constraintlayout - then remove the view from it. You don't need to call .Gone.
                    //todo: remove view
                }
            }
        }
    }

    public void changeStarSize(ImageView star, int newSize) {
        ConstraintLayout constraintLayout = starActivity.findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(newSize, newSize);
        constraintLayout.removeView(star);
        constraintLayout.addView(star, -1, lp);
    }

    public double findSizeAfterConsuming(ImageView consumer, ImageView consumed) {
        double totalOriginalSize = ((consumer.getWidth() * .25) * (consumer.getHeight() * .25)) / 1000;
        double totalConsumedSize = ((consumed.getWidth() * .25) * (consumed.getHeight() * .25)) / 1000;
        double combinedSize = totalConsumedSize + totalOriginalSize;
        return combinedSize / totalOriginalSize;
    }
}
