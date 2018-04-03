package com.example.sword.rpg;

import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import java.lang.Math;



import java.util.Random;

public class TiltCommandFragment extends Fragment implements SensorEventListener {

    private int direction;
    private View view;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private tiltDetector.OnTiltListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define view
        view = inflater.inflate(R.layout.fragment_tilt_command, container, false);
        Random rand = new Random();
        direction = rand.nextInt(4); // Get a random direction ([0, 3])
        updateText(direction); // Set the correct command title
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Inflate the layout for this fragment
        return view;
    }



    public void onSensorChanged(SensorEvent event) {
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            float gX = axisX / SensorManager.GRAVITY_EARTH;
            float gY = axisY / SensorManager.GRAVITY_EARTH;
            float gZ = axisZ / SensorManager.GRAVITY_EARTH;


            float gForce = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);
            if(gX<0.4 && gX>-0.4 && gY>-0.3 && gY<0.3&& gZ>0.7 && gZ<1.3){
                tiltDetected(0);
            }else if(gX<-0.4){
                tiltDetected(1);
            }else if(gX<0.4 && gX>-0.4 && gY>0.9 && gY<1.1&& gZ>0&& gZ<0.5 ){
                tiltDetected(2);
            }else if(gX>0.4){
                tiltDetected(3);
            }
    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }  @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    private void tiltDetected(int detected) {
        // On a correct swipe:
        if (detected == direction) {
            // Command succeeded
            ((SoloGame)getActivity()).commandFinished(true);
        } else { // (on wrong swipe:)
            // Command failed
            ((SoloGame)getActivity()).commandFinished(false);
        }
    }

    private void updateText(int direction) {
        // The command title
        TextView textView = view.findViewById(R.id.command_title);
        String text = "Tilt ";

        // Add the correct direction to the command title
        switch (direction) {
            case 0: text += "up!";
                break;
            case 1: text += "right!";
                break;
            case 2: text += "down!";
                break;
            case 3: text += "left!";
                break;
        }

        // Update the command title text
        textView.setText(text);
    }
}
