package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoonActivity extends AppCompatActivity {
double angle;
ImageView sun;
ImageView earth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View
    {
        Paint paint = null;
        double alpha=0;
        double beta=0;
        Boolean bRun=true;
        List<Float> xHist;
        List<Float> yHist;
        public MyView(Context context)
        {
            super(context);
            paint = new Paint();
            final Handler h = new Handler();
            h.postDelayed(new Runnable()
            {

                @Override
                public void run()
                {
                    // do stuff then
                    // can call h again after work!
                    MyView.this.invalidate();
                    h.postDelayed(this, 20);
                }
            }, 1000);
            xHist = new ArrayList<>();
            yHist = new ArrayList<>();
            this.setOnClickListener(this::onCClick);
        }
        public void onCClick(View v)
        {
            bRun = !bRun;
        }

        @Override
        protected void onDraw(Canvas canvas)
        {

            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int iRES = 350;
            int iREM = 75;
            if(bRun) {
                alpha -= 0.005;
                beta -= 0.065;
            }
            int xs=x/2;
            int ys=y/2;


            float xe=(float)(xs+Math.cos(alpha)*iRES);
            float ye=(float)(ys+Math.sin(alpha)*iRES);

            float xe2=(float)(xs+Math.cos(alpha)*iRES + 15*Math.cos(beta+3.14));
            float ye2=(float)(ys+Math.sin(alpha)*iRES + 15*Math.sin(beta+3.14));

            if(bRun) {
                xHist.add(xe2);
                yHist.add(ye2);
            }
            if(xHist.size()>1500) {
                xHist.remove(0);
                yHist.remove(0);
            }

            float xm=(float)(xe+Math.cos(beta)*iREM);
            float ym=(float)(ye+Math.sin(beta)*iREM);

            int iRadSun =50, iRadEarth=20, iRadMood=5;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#ffff00"));
            canvas.drawCircle(xs, ys, iRadSun, paint);
            paint.setColor(Color.parseColor("#0000ff"));
            canvas.drawCircle(xe2, ye2, iRadEarth, paint);
            paint.setColor(Color.parseColor("#101010"));
            canvas.drawCircle(xm, ym, iRadMood, paint);
            paint.setColor(Color.parseColor("#000000"));
            canvas.drawCircle(xe, ye, iRadMood, paint);
            paint.setColor(Color.parseColor("#ff0000"));

            for(int i=0;i<xHist.size();i++)
                canvas.drawCircle(xHist.get(i), yHist.get(i),iRadMood,paint);
        }

    }

}
