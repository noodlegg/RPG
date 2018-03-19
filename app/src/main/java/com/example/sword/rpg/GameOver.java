package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by s152706 on 13-Mar-18.
 */

public class GameOver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

    ImageButton mHomeButton = (ImageButton) findViewById(R.id.homeButton);
    //when you press the Home button you go to the home actiivty
    mHomeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
            // Intent is what you use to start another activity
            // we start the Home Activity with this intent
            Intent intent = new Intent(GameOver.this, Home.class);
            GameOver.this.startActivity(intent);
        }
    });
    }
}
