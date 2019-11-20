package com.example.astroapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SynodicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {
        Paint paint = null;
        double alpha = 0;
        double beta = 0;
        double gamma=0;
        Boolean bRun = false;
        List<Float> xHist;
        List<Float> yHist;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            final Handler h = new Handler();
            h.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // do stuff then
                    // can call h again after work!
                    SynodicActivity.MyView.this.invalidate();
                    h.postDelayed(this, 20);
                }
            }, 1000);
            xHist = new ArrayList<>();
            yHist = new ArrayList<>();
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
                gamma -= 0.005;
            }

            int xs=x/2;
            int ys=y/2;

            int rE=250;
            int rM=(int)(1.5*rE);


            float xe=(float)(xs+Math.cos(alpha)*rE);
            float ye=(float)(ys+Math.sin(alpha)*rE);

            float xm=(float)(xs+Math.cos(beta)*rM);
            float ym=(float)(ys+Math.sin(beta)*rM);




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



        }
    }
}
