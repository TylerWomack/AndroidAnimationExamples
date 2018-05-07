package com.example.twomack.animationtest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class RunningAnimationActivity extends AppCompatActivity {

    AnimationDrawable rainAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_layout);

        final ImageView raincloud = (ImageView) findViewById(R.id.runningman);
        raincloud.setImageResource(R.drawable.animation);
        rainAnimation = (AnimationDrawable) raincloud.getDrawable();

        raincloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rainAnimation.start();
            }
        });

    }

}
