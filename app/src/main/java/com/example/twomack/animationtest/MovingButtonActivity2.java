package com.example.twomack.animationtest;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MovingButtonActivity2 extends AppCompatActivity {

    ObjectAnimator animation;
    Button button;
    int count;
    //have an array of buttons?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moving_button2);

        count = 0;

        final Button buttonOrig = findViewById(R.id.button5);
        buttonOrig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewButton(v);
                v.setOnClickListener(null);
            }
        });
    }

    public void createNewButton(View v){
        button = new Button(this);
        button.setText("Click Me");
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        button.setX(v.getX());
        button.setY(v.getY());
        constraintLayout.addView(button, -1, lp);

        animate(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewButton(v);
                v.setOnClickListener(null);
            }
        });
    }

    private void animate(Button button){

        String propertyName;
        float position;

        switch (count){
            case 0:
                propertyName = "translationY";
                position = button.getY() + 800;
                setAnimation(button, propertyName, position);
                count++;
                break;
            case 1:
                propertyName = "translationX";
                position = button.getX() + 800;
                setAnimation(button, propertyName, position);
                count++;
                break;
            case 2:
                propertyName = "translationY";
                position = button.getY() - 800;
                setAnimation(button, propertyName, position);
                count++;
                break;
            case 3:
                propertyName = "translationX";
                position = button.getX() - 400;
                setAnimation(button, propertyName, position);
                count++;
                break;
            case 4:
                propertyName = "translationY";
                position = button.getY() + 400;
                setAnimation(button, propertyName, position);
                count++;
                break;
            case 5:
                propertyName = "translationX";
                position = button.getX() + 400;
                setAnimation(button, propertyName, position);
                count++;
                break;
            case 6:
                propertyName = "translationX";
                position = button.getX() - 800;
                setAnimation(button, propertyName, position);
                count++;
                break;
        }
    }

    private void setAnimation(Button button, String propertyName, float position){
        animation = ObjectAnimator.ofFloat(button, propertyName, position);
        animation.setDuration(500);
        animation.start();
    }
}
