package com.example.twomack.animationtest;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.twomack.animationtest.stars.FlingAnimationActivity;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable rainAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
    }

    public void startActivity1(View v){
        Intent intent = new Intent(this, RunningAnimationActivity.class);
        startActivity(intent);
    }

    public void startActivity2(View v){
        Intent intent = new Intent(this, AnimatedVectorDrawableActivity.class);
        startActivity(intent);
    }

    public void startActivity3(View v){
        Intent intent = new Intent(this, MovingButtonActivity.class);
        startActivity(intent);
    }

    public void startActivity4(View v){
        Intent intent = new Intent(this, FlingAnimationActivity.class);
        startActivity(intent);
    }

    public void startActivity5(View v){
        Intent intent = new Intent(this, MovingButtonActivity2.class);
        startActivity(intent);
    }
}
