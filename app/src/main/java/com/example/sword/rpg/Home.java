package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button soloButton = (Button) findViewById(R.id.solo_mode);
        soloButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {startSoloGame(); }
        });
        mTextMessage = (TextView) findViewById(R.id.message);
        Button shakeButton = (Button) findViewById(R.id.shake_mechanic);
        shakeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {startShakeMechanic(); }
        });
        Button swipeButton = (Button) findViewById(R.id.swipe_mechanic);
        swipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {startSwipeMechanic(); }
        });
        Button compassButton = (Button) findViewById(R.id.compass_mechanic);
        compassButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {startCompassMechanic(); }
        });
    }

    protected void startShakeMechanic() {
        Intent intent = new Intent(this, ShakeMechanic.class);
        startActivity(intent);
    }

    protected void startSwipeMechanic() {
        Intent intent = new Intent(this, SwipeMechanic.class);
        startActivity(intent);
    }

    protected void startCompassMechanic() {
        Intent intent = new Intent(this, CompassMechanic.class);
        startActivity(intent);
    }

    protected void startSoloGame() {
        Intent intent = new Intent(this, SoloGame.class);
        startActivity(intent);
    }

}
