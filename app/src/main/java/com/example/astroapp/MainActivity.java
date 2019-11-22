package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshHighscore();
    }
    private  void refreshHighscore()
    {
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("default",Context.MODE_PRIVATE);
        int highScore = sharedPref.getInt("highscore", 0);
        TextView txt = findViewById(R.id.txtHighScore);
        txt.setText("highscore: "+ highScore);
        txt.setTextColor(Color.rgb(0,0,255));
    }
    private void resetHighscore()
    {
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("default",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("highscore", 0);
        editor.commit();
        refreshHighscore();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuReset:
                resetHighscore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHighscore();
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
