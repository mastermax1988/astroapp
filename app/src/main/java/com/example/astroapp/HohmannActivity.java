package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HohmannActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {
        Paint paint = null;
        double alpha = 0;
        double beta = -0.9;
        double gamma=0;
        Boolean bRun = false;
        int pcount=0;
        List<Float> xHistE;
        List<Float> yHistE;
        List<Float> xHistM;
        List<Float> yHistM;
        List<Float> xHistS;
        List<Float> yHistS;
        public MyView(Context context) {
            super(context);
            paint = new Paint();
            final Handler h = new Handler();
            h.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // do stuff then
                    // can call h again after work!
                    HohmannActivity.MyView.this.invalidate();
                    h.postDelayed(this, 20);
                }
            }, 1000);
            xHistE = new ArrayList<>();
            yHistE = new ArrayList<>();
            xHistM = new ArrayList<>();
            yHistM = new ArrayList<>();
            xHistS = new ArrayList<>();
            yHistS = new ArrayList<>();

            this.setOnClickListener(this::onCClick);
        }

        public void onCClick(View v) {
            bRun = !bRun;
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();

            if(bRun) {
                alpha -= 0.0094;
                beta -= 0.005;
                gamma -= 0.007;
            }

            int xs=x/2;
            int ys=y/2;

            int rE=250;
            int rM=(int)(1.5*rE);
            int rH=(int)(Math.sqrt(1.26*1.26*rE*rE-0.26*rE*0.26*rE)/Math.sqrt(1-0.21*0.21*Math.cos(gamma)*Math.cos(gamma)));

            float xe=(float)(xs+Math.cos(alpha)*rE);
            float ye=(float)(ys+Math.sin(alpha)*rE);

            float xm=(float)(xs+Math.cos(beta)*rM);
            float ym=(float)(ys+Math.sin(beta)*rM);

            float xH=(float)(xs-0.26*rE+Math.cos(gamma)*rH);
            float yH=(float)(ys+Math.sin(gamma)*rH);

            if(bRun && pcount%10==0)
            {
                xHistE.add(xe);
                yHistE.add(ye);
                xHistM.add(xm);
                yHistM.add(ym);
                xHistS.add(xH);
                yHistS.add(yH);
                pcount=0;
            }
            if(bRun)
                pcount++;

            if(xHistE.size()>150) {
                xHistE.remove(0);
                yHistE.remove(0);
                xHistM.remove(0);
                yHistM.remove(0);
                xHistS.remove(0);
                yHistS.remove(0);
            }

            int iRadSun =50, iRadEarth=20, iRadMars=10;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            paint.setColor(Color.parseColor("#ffff00"));
            canvas.drawCircle(xs, ys, iRadSun, paint);
            paint.setColor(Color.parseColor("#0000ff"));
            canvas.drawCircle(xe, ye, iRadEarth, paint);
            paint.setColor(Color.parseColor("#ff0000"));
            canvas.drawCircle(xm, ym, iRadMars, paint);
            paint.setColor(Color.parseColor("#ff00ff"));
            canvas.drawCircle(xH, yH, iRadMars, paint);
            paint.setColor(Color.parseColor("#000000"));
            for(int i=0;i<xHistE.size();i++)
            {
                canvas.drawCircle(xHistE.get(i), yHistE.get(i),4,paint);
                canvas.drawCircle(xHistM.get(i), yHistM.get(i),4,paint);
                canvas.drawCircle(xHistS.get(i), yHistS.get(i),4,paint);
            }


        }
    }
}
