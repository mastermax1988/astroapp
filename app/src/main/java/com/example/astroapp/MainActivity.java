package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnMoonClick(View v)
    {
        Intent intent = new Intent(this, MoonActivity.class);
        startActivity(intent);
    }

    public void btnHohmannClick(View v)
    {
        Intent intent = new Intent(this, HohmannActivity.class);
        startActivity(intent);
    }

    public void btnSynodicClick(View v)
    {
        Intent intent = new Intent(this, SynodicActivity.class);
        startActivity(intent);
    }

    public void btnBallClick(View v)
    {
        Intent intent = new Intent(this, BallActivity.class);
        startActivity(intent);
    }
}
