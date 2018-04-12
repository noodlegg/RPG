package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SoloGame extends Game {
    @Override
    public void commandFinished(boolean success) {
        timerFragment.stopTimer();

        if (success == !isInverted) {
            score++; // Increase score by one
            showCorrectScreen();
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        // Go to the Game Over screen
        Intent intent = new Intent(this, GameOverSolo.class);
        Bundle bundle = new Bundle(); // New Bundle to pass score to Game Over screen
        bundle.putInt("score", score); // Add score to bundle
        intent.putExtras(bundle); // Add bundle to intent
        startActivity(intent); // Start the intent
    }

    @Override
    protected void displayNewCommand(CommandTriplet command) {
        super.displayNewCommand(command);
        // Create command title
        String text = command.getCommand().getTitle(); // Fetch title from command
        if (isInverted) { // If the command is inverted:
            text = "don't " + text; // Add "don't" at the start
        }
        text = text.substring(0, 1).toUpperCase() + text.substring(1); // Capitalize text
        TextView title = findViewById(R.id.command_title);
        title.setText(text); // Display title
    }
}
