package com.example.sword.rpg;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CompassCommandFragment extends Command implements SensorEventListener {
    private ImageView image;
    private ImageView unicorn;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager ;
    private TextView tvHeading;
    private int direction;
    private View view;


    private int north = 45;
    private int east = 135;
    private int south = 225;
    private int west = 315;
    private int marge = 1;
    private int graden;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_compass_command, container, false);
        // define the display assembly compass picture
        image = (ImageView) view.findViewById(R.id.unicorn);
        unicorn=(ImageView) view.findViewById(R.id.imageViewCompass);
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) view.findViewById(R.id.tvHeading);
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        return view;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree =Math.round(event.values[0]);
        tvHeading.setText(Float.toString(degree));

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        RotateAnimation boo = new RotateAnimation(
                graden,
                graden,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = degree;
        unicorn.startAnimation(boo);

        switch (direction) {
            case 0: graden=north;
                break;
            case 1:  graden=east;
                break;
            case 2:  graden=south;
                break;
            case 3:  graden=west;
                break;
        }

        if (currentDegree <= north+marge && currentDegree >= north-marge){
            compDetected(0);
        } else if(currentDegree <= east+marge && currentDegree >= east-marge){
            compDetected(1);
        }else if (currentDegree <= south+marge && currentDegree >= south-marge){
            compDetected(2);
        }else if (currentDegree <= west+marge && currentDegree >= west-marge){
            compDetected(3);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }


    private void compDetected(int detected) {
        // On a correct swipe:
        if (detected == direction) {
            // Command succeeded
            ((SoloGame)getActivity()).commandFinished(true);
        }
//          else { // (on wrong swipe:)
//            // Command failed
//            ((SoloGame)getActivity()).commandFinished(false);
//        }
    }
    @Override
    public String getTitle() {
        String title; // The command title

        Random rand = new Random();
        direction = rand.nextInt(4); // Get a random direction ([0, 3])

        title = "Turn "; // Title starts with "swipe"

        // Add the correct direction to the command title
        switch (direction) {
            case 0: title += "North! ("+Integer.toString(north)+")";
                break;
            case 1: title += "East! ("+Integer.toString(east)+")";
                break;
            case 2: title += "South! ("+Integer.toString(south)+")";
                break;
            case 3: title += "West! ("+Integer.toString(west)+")";
                break;
        }

        return title;
    }
}
