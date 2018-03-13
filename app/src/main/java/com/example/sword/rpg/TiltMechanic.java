package com.example.sword.rpg;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TiltMechanic extends AppCompatActivity {

    private TextView mCommandTitle;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TiltDetector mTiltDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilt_mechanic);

        // TiltDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mTiltDetector = new TiltDetector();
        mTiltDetector.setOnTiltListener(new TiltDetector.OnTiltListener() {
            @Override
            public void onWrongTilt(int count) {
                handleWrongTiltEvent(count);
            }
            @Override
            public void onTilt(int count) {
                handleTiltEvent(count);
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mTiltDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mTiltDetector);
        super.onPause();
    }

    public void handleTiltEvent(int count) {
        Snackbar.make(findViewById(R.id.tilt_mechanic_activity), "Yay, u did it!", 2000).show();
    }
    public void handleWrongTiltEvent(int count) {
        Snackbar.make(findViewById(R.id.tilt_mechanic_activity), "n00b", 2000).show();
    }
}
