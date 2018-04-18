package com.example.sword.rpg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Game Over screen that is shown at the end of a Solo Game.
 * It contains both the score that was achieved in this game, as well as the user's personal
 * high score.
 */
public class GameOverSolo extends AppCompatActivity {
    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_solo);

        // The Home button
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {toHome(); }
        });

        // The Retry button
        Button retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {playAgain(); }
        });

        // Set the correct score in the text
        TextView scoreTextView = findViewById(R.id.score);
        Bundle bundle = getIntent().getExtras(); // Get the Bundle (with the score)
        Integer score = bundle.getInt("score"); // Fetch score from bundle
        scoreTextView.setText(score.toString()); // Set score as text

        // Handle high scores
        TextView highScoreText = findViewById(R.id.text_highscore);
        handleHighScores(highScoreText, score);
    }

    /**
     * Handles high score checks and displays.
     * Checks if a high score was beaten and if so, updates it.
     * Also displays the (old) high score.
     * @param highScoreText  TextView in layout that should display the high score
     * @param score  score from recently finished game
     */
    private void handleHighScores(TextView highScoreText, Integer score) {
        // Get shared data (preferences)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Integer oldHighScore = prefs.getInt("highScore", 0); // Get high score from preferences

        // If the old high score was beaten:
        if (score > oldHighScore) {
            // Display the old high score
            highScoreText.setText("Previous high score: " + oldHighScore);

            SharedPreferences.Editor editor = prefs.edit(); // Create editor to change shared data
            editor.putInt("highScore", score); // Update the high score
            editor.apply(); // Apply changes
            highScore = score;
        } else { // (if the high score was not beaten: )
            // Display current (unbeaten) high score
            highScoreText.setText("High score: " + oldHighScore);
            highScore = oldHighScore;
        }
    }

    /**
     * Opens the Home screen.
     */
    protected void toHome() {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("highScore", highScore); // Add score to bundle
        // Retrieve username from SoloGame activity
        String username = getIntent().getStringExtra("username");
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    /**
     * Starts a new solo game
     */
    protected void playAgain() {
        Intent intent = new Intent(this, SoloGame.class);
        startActivity(intent);
        finish();
    }
}
