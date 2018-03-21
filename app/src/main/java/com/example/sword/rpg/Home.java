package com.example.sword.rpg;

import android.app.Fragment;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_leaderboard:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button playSolo = (Button) findViewById(R.id.solo_mode);
        Button playParty = (Button) findViewById(R.id.party_mode);
        Button playOnline = (Button) findViewById(R.id.online_mode);
        // Solo opens SwipeMechanic activity
        playSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSwipeMechanic();
            }
        });
        // Party opens PartyNameInput activity
        playParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPartyNameInput();
            }
        });
        // Online opens MultiplayerConnection activity
        playOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMC();
            }
        });
    }

    public void startSwipeMechanic() {
        Intent intent = new Intent(this, SwipeMechanic.class);
        startActivity(intent);
    }

    public void startPartyNameInput() {
        Intent intent = new Intent(this, PartyNameInput.class);
        startActivity(intent);
    }

    public void startShakeMechanic() {
        Intent intent = new Intent(this, ShakeMechanic.class);
        startActivity(intent);
    }

    public void startMC() {
        Intent intent = new Intent(this, MultiplayerConnection.class);
        startActivity(intent);
    }

}
