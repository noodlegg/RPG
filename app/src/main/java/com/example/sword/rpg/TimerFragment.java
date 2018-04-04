package com.example.sword.rpg;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


public class TimerFragment extends Fragment {

    private ProgressBar mProgressBar; // The progressbar timer

    private boolean mIsRunning; // Whether the timer should keep running
    private boolean mIsPaused = false; // Whether the timer is paused

    private long timeLeft; // Time left in milliseconds
    private long timeLimit; // Time limit in milliseconds
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
            timeLeft = timeLimit - (System.currentTimeMillis() - startTime);
            mProgressBar.setProgress((int) (1000 * timeLeft / timeLimit));

            // If the time is up
            if (timeLeft <= 0) {
                // Fail the command
                if (getActivity() != null) {
                    ((SoloGame)getActivity()).commandFinished(false);
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
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        mProgressBar = view.findViewById(R.id.timer);

        mIsRunning = true; // Yes, we want the timer to keep running
  //      timeLimit = getArguments().getLong("timeLimit", 1500); // Set timer duration
        timeLimit = getArguments().getLong("timeLimit", 1500); // Set timer duration
        startTime = System.currentTimeMillis(); // Record the start time

        System.out.println("Time for this round: " + timeLimit);

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

    /**
     * Kills the timer
     */
    public void stopTimer() {
        mIsRunning = false; // No, we don't want the timer to continue
        timerHandler.removeCallbacks(timerRunnable); // Stop handler calls
    }

    /**
     * Initializes the timer and its time limit.
     * The higher the score, the lower the time limit is.
     * Then, this time limit is multiplied with the difficulty, allowing it to be slightly more
     * difficult or easy.
     * @param score  so far accumulated score
     * @param difficulty  difficulty of this specific command
     * @return
     */
    public static TimerFragment newInstance(int score, double difficulty) {
        TimerFragment timerFragment = new TimerFragment(); // Create new timer

        double MAX_TIME = 3000; // Largest time limit in ms (for first round)
        double MIN_TIME = 1000; // Shortest time limit in ms (asymptote for time function)

        /* Function that returns a smaller and smaller value, as the game progresses, but never
           less than MIN_TIME */
        long timeLimit = (long) (3 * (MAX_TIME - MIN_TIME) / (0.6 * score + 3) + MIN_TIME);
        // Adapt time limit to difficulty
        timeLimit *= difficulty;

        Bundle args = new Bundle(); // Bundle to pass arguments
        args.putLong("timeLimit", timeLimit); // Add argument to bundle
        timerFragment.setArguments(args); // Pass bundle to the timer

        return timerFragment; // Return the timer
    }
}
