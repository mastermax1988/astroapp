package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class MoonActivity extends AppCompatActivity {
double angle;
ImageView sun;
ImageView earth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);
        sun = findViewById(R.id.imgSun);
        earth = findViewById(R.id.imgEarth);
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                // do stuff then
                // can call h again after work!
                draw();
                h.postDelayed(this, 20);
            }
        }, 1000);


    }
    public void draw()
    {
        earth.setX(earth.getX()+1);
    }

}
