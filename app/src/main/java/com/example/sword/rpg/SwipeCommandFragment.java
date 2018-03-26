package com.example.sword.rpg;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SwipeCommandFragment extends Fragment {

    private int direction;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define view
        view = inflater.inflate(R.layout.fragment_swipe_command, container, false);

        ImageView arrow = view.findViewById(R.id.arrow); // Arrow image
        /*Matrix matrix = new Matrix(); // Matrix to define scale and rotation of image
        arrow.setScaleType(ImageView.ScaleType.MATRIX); // Set scale type of image to MATRIX*/

        Random rand = new Random();
        direction = rand.nextInt(4); // Get a random direction ([0, 3])

        // Rotate the matrix
        /*matrix.postRotate((float) (90 * direction),
                arrow.getDrawable().getBounds().width()/2,
                arrow.getDrawable().getBounds().height()/2);
        arrow.setImageMatrix(matrix); // Match scale of image to the matrix*/

        arrow.setRotation(90* direction);
        updateText(direction); // Set the correct command title


        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        System.out.println("onFling() has been called");
                        final int SWIPE_MIN_DISTANCE = 150; // To detect long enough swipes
                        final int SWIPE_MAX_OFF_PATH = 150; // To ignore diagonal swipes
                        final int SWIPE_THRESHOLD_VELOCITY = 200; // To ignore slow swipes

                        // If the swipe wasn't "too vertical", check horizontal swipes
                        if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MAX_OFF_PATH) {
                            // To the left
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                swipeDetected(3);

                            // To the right
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                swipeDetected(1);
                            }
                        }

                        //If the swipe wasn't "too horizontal", check vertical swipes
                        if (Math.abs(e1.getX() - e2. getX()) < SWIPE_MAX_OFF_PATH) {
                            // Upward
                            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                swipeDetected(0);

                            // Downward
                            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                                swipeDetected(2);
                            }
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void swipeDetected(int detected) {
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
        String text = "Swipe ";

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
