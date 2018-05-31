package com.example.twomack.animationtest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MovingButtonActivity extends AppCompatActivity {

    Button button;
    int timesPressed;
    ObjectAnimator initialAnimation;
    ObjectAnimator animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movingbuttons);
        timesPressed = 0;

        button = findViewById(R.id.movableButton);


        initialAnimation = ObjectAnimator.ofFloat(button, "translationX", -200f);
        initialAnimation.setDuration(0);
        initialAnimation.start();

        //initializing animation
        animation = ObjectAnimator.ofFloat(button, "translationY", 0f);


        initialAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startAnim(button.getX(), button.getX() + 400, "translationX", 1000L);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (timesPressed){
                    case 0:
                        animation = ObjectAnimator.ofFloat(button, "Y", 1100f);
                        animation.setDuration(1000l);
                        animation.start();
                        button.setBackgroundColor(Color.MAGENTA);
                        timesPressed++;
                        break;
                    case 1:
                        startAnim(button.getX(), button.getX() + 400, "X", 600l);
                        timesPressed++;
                        button.setBackgroundColor(Color.BLUE);
                        break;
                    case 2:
                        startAnim(button.getY(), button.getY() - 1000, "Y", 800l);
                        timesPressed++;
                        button.setBackgroundColor(Color.CYAN);
                        break;
                    case 3: startAnim(button.getX(), button.getX() - 400, "X", 1000l);
                        timesPressed = 0;
                        button.setBackgroundColor(Color.GRAY);
                        break;
                }
            }
        });
    }

    public void startAnim(float start, float end,  String property, long duration){
        animation.setPropertyName(property);
        animation.setFloatValues(start, end);
        animation.setDuration(duration);
        animation.start();
    }
}

