package com.example.sword.rpg;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;


public class TimerFragment extends Fragment {

    private ProgressBar mProgressBar;
    TimerTask updateTimer;

    long timePassed = 0; // Time passed in milliseconds
    int timeLimit = 500; // Maximum time for timer to run out
    long updateRate = 5; // Time step for updating the progress bar

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mProgressBar = view.findViewById(R.id.timer);
        mProgressBar.setProgress(0);

        updateTimer = new UpdateTimer();
        new Timer().scheduleAtFixedRate(updateTimer, updateRate, timeLimit);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        updateTimer.cancel();
    }

    class UpdateTimer extends TimerTask {
        public void run() {
            timePassed += 10;
            if (timePassed >= timeLimit) {
                cancel();
                ((SoloGame)getActivity()).commandFinished(false);
            }
            mProgressBar.setProgress((int) (1000 * (timeLimit - timePassed) / timeLimit));
            System.out.println(timePassed); //FOR TESTING PURPOSES
        }
    }
}
