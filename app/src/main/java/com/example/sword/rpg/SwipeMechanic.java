package com.example.sword.rpg;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Random;

public class SwipeMechanic extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    String inputSwipe = null;
    Random random = new Random();
    int scoreCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_mechanic);
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Swipe test");
        // Toggle button to switch between swipe test and gamme test
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    commandTest();
                } else {
                    textView.setText("Swipe test");
                    scoreCount = 0;
                }
            }
        });
        // Initiate gesture detector
        mDetector = new GestureDetectorCompat(this, this);
        // Set gesture detector as double tap listener
        mDetector.setOnDoubleTapListener(this);
    }

    // Broken af, fix with UI thread?
    public void commandTest() {
        TextView textView = (TextView) findViewById(R.id.textView);
        String[] commands = {"up", "down", "left", "right"};
        String command = commands[random.nextInt(4)];
        textView.setText(command);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        //Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        //Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        if (event1.getY() - event2.getY() > 150) {
            inputSwipe = "up";
            Toast.makeText(SwipeMechanic.this, "UP", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (event2.getY() - event1.getY() > 150) {
            inputSwipe = "down";
            Toast.makeText(SwipeMechanic.this, "DOWN", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (event1.getX() - event2.getX() > 50) {
            inputSwipe = "left";
            Toast.makeText(SwipeMechanic.this, "LEFT", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (event2.getX() - event1.getX() > 50) {
            inputSwipe = "right";
            Toast.makeText(SwipeMechanic.this, "RIGHT", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        //Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
}
