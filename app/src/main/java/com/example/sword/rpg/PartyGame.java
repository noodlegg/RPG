package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class PartyGame extends Game {
    PlayerTriplet currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        players = initializePlayers();
        super.onCreate(savedInstanceState);
    }

    protected ArrayList<PlayerTriplet> initializePlayers() {
        return (ArrayList) getIntent().getSerializableExtra("players");
    }

    @Override
    public void commandFinished(boolean success) {
        timerFragment.stopTimer();
        if (success == !isInverted) {
            currentPlayer.commandExecuted(true);
            showCorrectScreen();
        } else {
            currentPlayer.commandExecuted(false);
            showIncorrectScreen();
        }
    }

    @Override
    public void doNewCommand() {
        if (getSum() == 0) {
            gameOver();
        } else {
            super.doNewCommand();
        }
    }

    @Override
    protected void displayNewCommand(CommandTriplet command) {
        super.displayNewCommand(command);

        currentPlayer = getRandomPlayer();

        // Create command title
        String text = command.getCommand().getTitle(); // Fetch title from command
        if (isInverted) { // If the command is inverted:
            text = currentPlayer.getName() + ", don't " + text; // Add "don't" at the start
        } else {
            text = currentPlayer.getName() + ", " + text;
        }
        TextView title = findViewById(R.id.command_title);
        title.setText(text); // Display title
    }

    private PlayerTriplet getRandomPlayer() {
        int sum = getSum();
        int r = rand.nextInt(sum) + 1;
        for (PlayerTriplet player : players) {
            r -= player.getCommandsLeft();
            if (r <= 0) {
                return player;
            }
        }

        return null;
    }

    private int getSum() {
        int sum = 0; // Start out with 0

        for (PlayerTriplet player : players) {
            sum += player.getCommandsLeft();
        }

        return sum;
    }

    private void gameOver() {
        // Go to the Game Over screen
        Intent intent = new Intent(this, GameOverSolo.class);
        Bundle bundle = new Bundle(); // New Bundle to pass score to Game Over screen
        bundle.putInt("score", score); // Add score to bundle
        intent.putExtras(bundle); // Add bundle to intent
        startActivity(intent); // Start the intent
        finish();
    }
}
