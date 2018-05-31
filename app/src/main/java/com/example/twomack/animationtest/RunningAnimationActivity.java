package com.example.twomack.animationtest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class RunningAnimationActivity extends AppCompatActivity {

    AnimationDrawable runningAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_layout);

        final ImageView runningMan = findViewById(R.id.runningman);
        runningMan.setImageResource(R.drawable.animation);
        runningAnimation = (AnimationDrawable) runningMan.getDrawable();

        runningMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runningAnimation.start();
            }
        });

    }

}
