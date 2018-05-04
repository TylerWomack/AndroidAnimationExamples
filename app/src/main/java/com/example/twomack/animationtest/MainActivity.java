package com.example.twomack.animationtest;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable rainAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        //cloud animation activity
        //Intent intent = new Intent(this, CloudAnimationActivity.class);
        //startActivity(intent);

        //Intent intent2 = new Intent(this, AnimatedVectorDrawableActivity.class);
        //startActivity(intent2);

        //Intent intent3 = new Intent(this, MovingButtonActivity.class);
        //startActivity(intent3);



    }

    public void startActivity1(View v){
        Intent intent = new Intent(this, CloudAnimationActivity.class);
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
}
