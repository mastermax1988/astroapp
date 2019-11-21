package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallActivity extends AppCompatActivity {


    double ax,ay;
    Random rnd;
    int lastSpanw=0;
    public class Asteroid
    {
        public double x,y,vx,vy;
        int r;
        public Asteroid(double xp, double yp)
        {
            x=xp;
            y=yp;
            vx=0;
            vy=10;
            r=rnd.nextInt(10)+5;
        }
    }
    ArrayList<Asteroid> ast;
    SensorManager sm = null;
    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            ax=values[0];
            ay=values[1];
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
        rnd = new Random();
        ast = new ArrayList<>();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm.registerListener(sel,  sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onStop() {
        sm.unregisterListener(sel);
        super.onStop();
    }

    public class MyView extends View {
        Paint paint = null;
        double xb,yb;
        double vx,vy;
        double R=50;
        int iScore=0;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            final Handler h = new Handler();
            h.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // do stuff then
                    // can call h again after work!
                    BallActivity.MyView.this.invalidate();
                    lastSpanw+=20;
                    if(lastSpanw>1000)
                    {
                        ast.add(new Asteroid(rnd.nextDouble()*getWidth(),0));
                        lastSpanw=0;
                    }

                    h.postDelayed(this, 20);
                }
            }, 1000);

            vx=0;
            vy=0;
        }


        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            xb=getWidth()/2;
            yb=getHeight()/2.0;
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);
            vx-=ax/10.0;
            vy+=ay/10.0;
            xb+=vx;
            yb+=vy;
            if(xb<0)
                vx=Math.abs(vx);
            if(xb>getWidth())
                vx=-Math.abs(vx);
            if(yb<0)
                vy=Math.abs(vy);
            if(yb>getHeight())
                vy=-Math.abs(vy);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#0000ff"));
            canvas.drawCircle((int)xb, (int)yb,(float)R, paint);
            paint.setTextSize(30f);
            canvas.drawText("Score: " + iScore,100,100,paint );
            paint.setColor(Color.parseColor("#ff0000"));
            for (Asteroid a: ast)
            {
                a.x+=a.vx;
                a.y+=a.vy;
                canvas.drawCircle((int)a.x,(int)a.y,a.r,paint);
            }
            int i=0;
            while(i<ast.size())
            {
                if(ast.get(i).y>getHeight())
                    ast.remove(i);
                else
                    i++;
            }

        }
    }


}
