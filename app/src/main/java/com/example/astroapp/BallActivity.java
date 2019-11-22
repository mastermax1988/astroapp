package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;

import java.util.Random;

public class BallActivity extends AppCompatActivity {


    double ax,ay;
    Random rnd;
    int lastSpanw=0;
    public class Asteroid
    {
        public double x,y,vx,vy;
        int r;
        int type;//0:normal, 1:super, 2:punish
        public Asteroid(double xp, double yp, int score)
        {
            x=xp;
            y=yp;
            vx=rnd.nextDouble()*4-2;
            vy=10;
            r=rnd.nextInt(15)+5;
            vy+=r/5.0;
            int rand=rnd.nextInt(10);
            switch (rand)
            {
                case 0:
                    type=1;
                    break;
                case 1:
                    type=score>10?2:1;
                    break;
                 default:
                     type=0;

            }
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
        double time=20d;
        Boolean bRun=true;
        double nextSpawn=500;
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
                    if(bRun) {
                        lastSpanw += 20;
                        time -= 0.02;
                    }
                    if(lastSpanw>nextSpawn)
                    {
                        ast.add(new Asteroid(rnd.nextDouble()*getWidth(),0,iScore));
                        lastSpanw=0;
                        nextSpawn=250+rnd.nextInt(500);
                    }

                    if(time<=0) {
                        bRun = false;
                        time = 0;
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
            if(bRun) {
                vx -= ax / 10.0;
                vy += ay / 10.0;
                xb += vx;
                yb += vy;
                if (xb < 0)
                    vx = Math.abs(vx);
                if (xb > getWidth())
                    vx = -Math.abs(vx);
                if (yb < 0)
                    vy = Math.abs(vy);
                if (yb > getHeight())
                    vy = -Math.abs(vy);
            }

            paint.setStyle(Paint.Style.FILL);
            int iCol=Math.min(255,(int)(Math.abs(time)/10.0*255));
            paint.setColor(Color.rgb(iCol,iCol,iCol));
            canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#0000ff"));
            canvas.drawCircle((int)xb, (int)yb,(float)R, paint);
            paint.setTextSize(100f);
            canvas.drawText("Score: " + iScore+"   Time: "+Math.round(time*10)/10.0,100,100,paint );
            paint.setColor(Color.parseColor("#ff0000"));
            for (Asteroid a: ast)
            {
                if(bRun){
                    a.x+=a.vx;
                    a.y+=a.vy+iScore/5.0;
                }
                int c=0;
                switch (a.type)
                {
                    case 0:
                        c=Color.parseColor("#000000");
                        break;
                    case 1:
                        c=Color.parseColor("#00ff00");
                        break;
                    case 2:
                        c=Color.parseColor("#ff0000");
                        break;
                }
                paint.setColor(c);
                canvas.drawCircle((int)a.x,(int)a.y,a.r,paint);
            }
            int i=0;
            while(i<ast.size())
            {
                if(Math.sqrt(Math.pow(xb-ast.get(i).x,2)+Math.pow(yb-ast.get(i).y,2))<=(R+ast.get(i).r))
                {
                    iScore++;
                    switch (ast.get(i).type)
                    {
                        case 0:
                            time++;
                            break;
                        case 1:
                            time+=3;
                            break;
                        case 2:
                            time-=3;
                            break;
                    }
                    ast.remove(i);
                    return;
                }
                if(ast.get(i).y>getHeight())
                    ast.remove(i);
                else
                    i++;
            }

        }
    }


}
