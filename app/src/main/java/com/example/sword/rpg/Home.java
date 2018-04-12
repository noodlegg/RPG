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

        Button partyButton = (Button) findViewById(R.id.party_mode);
        partyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {startPartyGame(); }
        });
    }

    protected void startSoloGame() {
        Intent intent = new Intent(this, SoloGame.class);
        startActivity(intent);
    }

    protected void startPartyGame() {
        Intent intent = new Intent(this, InputNames.class);
        startActivity(intent);
    }

}
