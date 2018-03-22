package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by s161756 on 22-3-2018.
 */

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {toHome(); }
        });
        Button retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {playAgain(); }
        });
    }

    protected void toHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    protected void playAgain() {
        Intent intent = new Intent(this, SoloGame.class);
        startActivity(intent);
    }
}
