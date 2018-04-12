package com.example.sword.rpg;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class IncorrectFragment extends Fragment {

    private boolean mIsRunning; // Whether the timer should keep running
    private boolean mIsPaused = false; // Whether the timer is paused

    private long timePassed=0; // Time passed in milliseconds
    private long timeLimit = 50000; // Time limit in milliseconds
    private long startTime; // Records the system time at which the timer started
    private long timeAtPause = 0; // The system time at the moment the game is paused

    private Handler timerHandler = new Handler(); // Handler used as timer
    private Runnable timerRunnable = new Runnable() { // Handler Runnable

        @Override
        public void run() {
            if (!mIsRunning) { // If the timer should stop
                return; // Stop the timer
            }

            // Update timer
            timePassed += (System.currentTimeMillis() - startTime);

            ImageView img= getView().findViewById(R.id.crossImage);

            //Remove check if 5,000 < t < 10,000
            if (timePassed > 5000 && timePassed < 10000) {
                img.setImageResource(R.drawable.empty);
            }
            //Display check if 10,000 < t < 15,000
            if (timePassed > 10000 && timePassed < 15000) {
                img.setImageResource(R.drawable.cross);
            }
            //Remove check if 15,000 < t < 20,000
            if (timePassed > 15000 && timePassed < 20000) {
                img.setImageResource(R.drawable.empty);
            }
            //Display check if t > 20000
            if (timePassed > 20000) {
                img.setImageResource(R.drawable.cross);
            }

            // If the time is up
            if (timePassed >= timeLimit) {
                // Proceed to next command
                if (getActivity() != null) {
                    mIsRunning = false; // No, we don't want the timer to continue
                    timerHandler.removeCallbacks(timerRunnable); // Stop handler calls

                    ((Game)getActivity()).doNewCommand(); // Get new command
                }
            }

            // Repeat after 5 ms
            timerHandler.postDelayed(this, 5);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incorrect, container, false);

        mIsRunning = true; // Yes, we want the timer to keep running
        startTime = System.currentTimeMillis(); // Record the start time

        timerHandler.postDelayed(timerRunnable, 0); // Call Handler Runnable

        return view;
    }

    @Override
    public void onPause() {
        // Save the current time, so we can recalibrate the timer when we resume it.
        timeAtPause = System.currentTimeMillis();
        //Pause timer
        timerHandler.removeCallbacksAndMessages(null);
        mIsPaused = true;

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsPaused) { // If the time has been paused:
            startTime += System.currentTimeMillis() - timeAtPause; // Recalibrate timer
        }

        // Resume timer
        mIsPaused = false;
        timerHandler.postDelayed(timerRunnable, 0);
    }
}
