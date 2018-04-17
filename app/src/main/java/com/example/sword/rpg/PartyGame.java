package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Extends the logic of Game to play a party game.
 * Each command is directed at one of the participating players (there are at least two). If this
 * particular player fulfills the command in time, they are rewarded with a point. Each player is
 * presented with the same amount of commands, but the order in which players are chosen is, just
 * like the order of the different commands, semi-random. After each player has been given all their
 * commands, the game ends.
 */
public class PartyGame extends Game {
    protected ArrayList<PlayerTriplet> players; // List of all participating players
    PlayerTriplet currentPlayer; // Player that has to perform the current command

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        players = initializePlayers();
        super.onCreate(savedInstanceState);
    }

    /**
     * Initializes the list of players
     * @return  a list with all the participating players
     */
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

    /**
     * Picks a random player from the ArrayList {@code players}.
     * For each player in the list, the probability weight for this player to be chosen is the
     * number of commands they have left. The change of the player "examplePlayer" being chosen is:
     * {@code p(examplePlayer) = examplePlayer.getCommandsLeft() / getSum()}
     * Pick a random command from the ArrayList {@code commands}.
     * @return one of the players in the ArrayList {@code players}
     */
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

    /**
     * Returns the sum of every player's getCommandsLeft().
     * This is equal to the number of commands that are left for the game to end.
     * @return  how many commands still have to be given out until the end of the game
     */
    private int getSum() {
        int sum = 0; // Start out with 0

        for (PlayerTriplet player : players) {
            sum += player.getCommandsLeft();
        }

        return sum;
    }

    /**
     * Sends the players to the game over screen.
     */
    private void gameOver() {
        // Go to the Game Over screen
        Intent intent = new Intent(this, GameOverParty.class);
        intent.putExtra("players", players); // Pass players to the game overs screen
        startActivity(intent);
        finish();
    }
}
