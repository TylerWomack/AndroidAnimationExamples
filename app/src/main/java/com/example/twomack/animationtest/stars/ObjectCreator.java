package com.example.twomack.animationtest.stars;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.example.twomack.animationtest.R;
import com.example.twomack.animationtest.stars.GestureListeners.FlingGestureListener;
import com.example.twomack.animationtest.stars.GestureListeners.SpringGestureListener;
import com.example.twomack.animationtest.stars.objects.Baddie;
import com.example.twomack.animationtest.stars.objects.Star;
import java.util.Random;
import static com.example.twomack.animationtest.stars.AppConstants.baddieStartingSizeMultiplier;
import static com.example.twomack.animationtest.stars.AppConstants.startingStarSize;

public class ObjectCreator {

    private FlingAnimationActivity starActivity;
    private Fling fling;
    private Random random;

    ObjectCreator(FlingAnimationActivity starActivity, Fling fling){
        this.starActivity = starActivity;
        this.fling = fling;
        this.random = new Random();
    }

    //old star is needed in the event that you are creating a new star when a star is splitting apart after hitting a wall
    public void createNewStar(float x, float y, float velocityY, float velocityX, @Nullable Star oldStar) {

        Star newStar = new Star(starActivity);
        starActivity.starArrayList.add(newStar);

        newStar = setImageAndColor(newStar);
        addStarToScreen(newStar, oldStar, x, y);

        GestureDetector newDetector = new GestureDetector(starActivity, new FlingGestureListener(newStar, fling, starActivity.getCollisionUtil()));
        setGestureDetector(newDetector, newStar);

        setInitialMotion(newStar, velocityY, velocityX);
    }



    private Star setImageAndColor(Star newStar){
        newStar.setImageDrawable(starActivity.getResources().getDrawable(android.R.drawable.btn_star_big_on));
        //I want a percentage of the new stars to be different colors, but most to be gold.
        if (random.nextInt(10) > 6) {
            newStar.setColorFilter(getRandomColor());
        }
        return newStar;
    }

    private void addStarToScreen(Star newStar, @Nullable Star oldStar, float x, float y){
        ConstraintLayout constraintLayout = starActivity.findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams lp = getLayoutParams(oldStar);
        constraintLayout.addView(newStar, -1, lp);

        //this is for when a star hits a wall and splits apart. It divides the star by two.
        if (oldStar != null)
            oldStar.setLayoutParams(lp);

        newStar.setX(x);
        newStar.setY(y);
    }

    private ConstraintLayout.LayoutParams getLayoutParams(@Nullable Star oldStar){

        ConstraintLayout.LayoutParams lp;
        if (oldStar == null) {
            lp = new ConstraintLayout.LayoutParams(startingStarSize, startingStarSize);
            if (starActivity.starArrayList.size() == 1) {
                lp = new ConstraintLayout.LayoutParams(startingStarSize * 5, startingStarSize * 5);
            }
        } else {
            lp = new ConstraintLayout.LayoutParams(oldStar.getHeight() / 2, oldStar.getWidth() / 2);
        }

        return lp;
    }

    private void setInitialMotion(Star newStar, float velocityY, float velocityX){
        if (velocityX != 0)
            fling.simpleXFling(newStar, velocityX);

        if (velocityY != 0)
            fling.simpleYFling(newStar, velocityY);
    }



    public void createBaddie() {
        Baddie baddie = new Baddie(starActivity);
        setBaddieInitialDirection(baddie);
        starActivity.baddieArrayList.add(baddie);
        addBaddieToScreen(baddie);
    }

    private void addBaddieToScreen(Baddie baddie){
        baddie.setImageDrawable(starActivity.getResources().getDrawable(android.R.drawable.radiobutton_on_background));
        ConstraintLayout constraintLayout = starActivity.findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(startingStarSize * baddieStartingSizeMultiplier, startingStarSize * baddieStartingSizeMultiplier);
        constraintLayout.addView(baddie, -1, lp);
        baddie.setColorFilter(Color.BLACK);
        baddie.setY((float) random.nextInt((int) starActivity.getScreenHeight()));
    }

    private void setBaddieInitialDirection(Baddie baddie){
        if (random.nextBoolean()) {
            baddie.setHorizontalFlingSpeed(starActivity.getBaddieSpeed());
        } else {
            baddie.setHorizontalFlingSpeed(0 - starActivity.getBaddieSpeed());
        }

        if (random.nextBoolean()) {
            baddie.setVerticalFlingSpeed(starActivity.getBaddieSpeed());
        } else {
            baddie.setVerticalFlingSpeed(0 - starActivity.getBaddieSpeed());
        }
    }

    private void addRectangle() {
        ImageView rectangle = starActivity.findViewById(R.id.bar);
        GestureDetector newDetector = new GestureDetector(starActivity, new SpringGestureListener(rectangle, fling, starActivity.getCollisionUtil(), starActivity));
        setGestureDetector(newDetector, rectangle);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setGestureDetector(final GestureDetector gestureDetector, ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void createInitialObjects(){
        createNewStar(starActivity.getScreenWidth() / 2, starActivity.getScreenHeight() / 2, 0, 0, null);
        createNewStar(200, 400, 0, 0, null);
        createNewStar(starActivity.getScreenWidth() / 3, starActivity.getScreenHeight() / 3, 0, 0, null);
        createNewStar(0, 600, 0, 0, null);
        createBaddie();
        addRectangle();
    }

    private int getRandomColor() {

        int randomNumber = random.nextInt(7);

        switch (randomNumber) {
            case 0:
                return starActivity.getResources().getColor(R.color.darkblue);
            case 1:
                return starActivity.getResources().getColor(R.color.green);
            case 2:
                return starActivity.getResources().getColor(R.color.darkblue);
            case 3:
                return starActivity.getResources().getColor(R.color.orange);
            case 4:
                return starActivity.getResources().getColor(R.color.purple);
            case 5:
                return starActivity.getResources().getColor(R.color.red);
            case 6:
                return starActivity.getResources().getColor(R.color.yellow);
        }
        return starActivity.getResources().getColor(R.color.lightblue);
    }

}
