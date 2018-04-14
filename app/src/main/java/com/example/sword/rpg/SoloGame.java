package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Extends the logic of Game to play a solo game.
 * If the user fulfills their command within the time limit, they are rewarded a point and are
 * presented with another task for this process to be repeated. Each new command has to be performed
 * in slightly less time than the previous one.
 * If the user fails to perform the given command in time, the game stops.
 */
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

    /**
     * Sends the player to the game over screen
     */
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
