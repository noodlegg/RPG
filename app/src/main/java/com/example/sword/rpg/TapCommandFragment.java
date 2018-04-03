package com.example.sword.rpg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.Random;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

public class TapCommandFragment extends Fragment {

    private int direction;
    private View view;
    private int n=0;
    private boolean isClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define view
        view = inflater.inflate(R.layout.fragment_tap_command, container, false);

        Random rand = new Random();
        direction = rand.nextInt(4); // Get a random direction ([0, 3])
        updateText(direction); // Set the correct command title
        ImageButton button = view.findViewById(R.id.button);

        isClicked=false;
        n=0;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (isClicked==false) {
                    n=0;
                    new CountDownTimer(900, 1000) {
                        public void onTick(long millisUntilFinished) {
                            Log.d("test",Long.toString(millisUntilFinished));
                        }

                        public void onFinish() {
                            if (n == 1) {
                                detected(0);
                                isClicked=false;
                            } else if (n == 2) {
                                detected(1);
                                isClicked=false;
                            } else if (n == 3) {
                                detected(2);
                                isClicked=false;
                            } else if (n == 4) {
                                detected(3);
                                isClicked=false;
                            } else {
                                detected(6);
                                isClicked=false;
                            }
                        }
                    }.start();
                    isClicked=true;
                }
                n++;
                Log.d("test",Integer.toString(n));
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        n=0;
        isClicked=false;
    }

    private void detected(int detected) {
        // On a correct swipe:
        if (detected == direction) {
            // Command succeeded
            ((SoloGame)getActivity()).commandFinished(true);
        }
        else { // (on wrong swipe:)
            // Command failed
            ((SoloGame)getActivity()).commandFinished(false);
        }
    }


    private void updateText(int direction) {
        // The command title
        TextView tapText = view.findViewById(R.id.tap_text);
        String text = "Tap ";

        // Add the correct direction to the command title
        switch (direction) {
            case 0:
                text += "1";
                break;
            case 1:
                text += "2";
                break;
            case 2:
                text += "3";
                break;
            case 3:
                text += "4";
                break;
        }

        // Update the command title text
        tapText.setText(text);
    }
}


