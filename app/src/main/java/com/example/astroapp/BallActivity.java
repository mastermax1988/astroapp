package com.example.astroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class BallActivity extends AppCompatActivity {

    SensorManager sm = null;
    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            txt.setText("x:"+values[0]+"\ny:"+values[1]+"\nz:"+values[2]);
        }
    };
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm.registerListener(sel,  sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
        txt=findViewById(R.id.txtAcc);
    }
    protected void onStop() {
        sm.unregisterListener(sel);
        super.onStop();
    }


}
