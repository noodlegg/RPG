package com.example.sword.rpg;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;
import android.util.Log;


public class TiltDetector implements SensorEventListener {

    /*
     * The gForce that is necessary to register as tilt.
     * Must be greater than 1G (one earth gravity unit).
     * You can install "G-Force", by Blake La Pierre
     * from the Google Play Store and run it to see how
     *  many G's it takes to register a tilt
     */
    private static final float TILT_THRESHOLD_GRAVITY = 0.1F;
    private static final float NOTILT_THRESHOLD_GRAVITY = 0.1F;
    private static final int TILT_SLOP_TIME_MS = 2500;
    private static final int TILT_COUNT_RESET_TIME_MS = 3000;

    private OnTiltListener mListener;
    private long mTiltTimestamp;
    private int mTiltCount;

    public void setOnTiltListener(OnTiltListener listener) {
        this.mListener = listener;
    }

    public interface OnTiltListener {
        public void onTilt(int count);
        public void onWrongTilt(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;



            // gForce will be close to 1 when there is no movement.
            float gForce = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);
            if (gX > TILT_THRESHOLD_GRAVITY && (gY*gY) < NOTILT_THRESHOLD_GRAVITY && (gZ*gZ) < NOTILT_THRESHOLD_GRAVITY) {
                Log.d("ADebugTag", "Value gX: " + Float.toString(gX));
                Log.d("ADebugTag", "Value gY: " + Float.toString(gY));
                Log.d("ADebugTag", "Value gZ: " + Float.toString(gZ));
                final long now = System.currentTimeMillis();
                // ignore tilt events too close to each other (500ms)
                if (mTiltTimestamp + TILT_SLOP_TIME_MS > now) {
                    return;
                }

                // reset the tilt count after 3 seconds of no tilts
                if (mTiltTimestamp + TILT_COUNT_RESET_TIME_MS < now) {
                    mTiltCount = 0;
                }

                mTiltTimestamp = now;
                mTiltCount++;

                mListener.onTilt(mTiltCount);
            }
            if ((gX<0&&(gX*gX) > NOTILT_THRESHOLD_GRAVITY&&(gY*gY) > NOTILT_THRESHOLD_GRAVITY && (gZ*gZ) > NOTILT_THRESHOLD_GRAVITY)||((gY*gY) > NOTILT_THRESHOLD_GRAVITY && (gZ*gZ) > NOTILT_THRESHOLD_GRAVITY))  {
                Log.d("ADebugTag", "Value gX: " + Float.toString(gX));
                Log.d("ADebugTag", "Value gY: " + Float.toString(gY));
                Log.d("ADebugTag", "Value gZ: " + Float.toString(gZ));
                final long now = System.currentTimeMillis();
                // ignore tilt events too close to each other (500ms)
                if (mTiltTimestamp + TILT_SLOP_TIME_MS > now) {
                    return;
                }

                // reset the tilt count after 3 seconds of no tilts
                if (mTiltTimestamp + TILT_COUNT_RESET_TIME_MS < now) {
                    mTiltCount = 0;
                }

                mTiltTimestamp = now;
                mTiltCount++;

                mListener.onWrongTilt(mTiltCount);
            }
        }
    }
}