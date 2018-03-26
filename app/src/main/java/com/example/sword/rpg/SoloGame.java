package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SoloGame extends AppCompatActivity {
    private HashMap<Fragment, Integer> commands = initializeCommands();
    private int score = 0;

    // For random numbers
    Random rand = new Random();

    // The Fragment of the timer progress bar
    TimerFragment timerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_game);
        doNewCommand(); // Proceed to game logic
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        /* Empty on purpose: during the game, the back button should not perform any action. */
    }

    /**
     * Handles the events after a command has ended.
     * If the command was executed successfully: give a new command;
     * if the command was not executed successfully: move to the Game Over screen.
     * @param success  whether the command was successfully executed or not
     */
    public void commandFinished(boolean success) {
        timerFragment.stopTimer();
        if (success) {
            score++; // Increase score by one
            doNewCommand(); // Give a new command
        } else {
            System.out.println("Score: " + score); // FOR TESTING PURPOSES
            // Go to the Game Over screen
            Intent intent = new Intent(this, GameOver.class);
            startActivity(intent);
        }
    }

    /**
     * Overall logic for giving a new command:
     * 1. Pick random command.
     * 2. Update each command's weight accordingly.
     * 3. Display new command.
     */
    private void doNewCommand() {
        Fragment command = getRandomCommand(); //Pick a random command

        /* FOR DEBUGGING PURPOSES */
        System.out.println("=====================================");
        System.out.println("Old weights:");
        printWeights();

        updateWeights(command); // Update the probability weights from the commands

        /* FOR DEBUGGING PURPOSES */
        System.out.println("New weights:");
        printWeights(); // FOR DEBUGGING PURPOSES

        displayNewCommand(command);
    }

    /**
     * Creates a HashMap with every possible command in there, each with probability weight 1.
     * @return a HashMap with every possible command, each with probability weight 1.
     */
    private HashMap<Fragment, Integer> initializeCommands() {
        HashMap<Fragment, Integer> hmap = new HashMap<>();

        // The names of the different commands:
        hmap.put(new GreenTestFragment(), 1);
        hmap.put(new RedTestFragment(), 1);
        hmap.put(new BlueTestFragment(), 1);
        hmap.put(new SwipeCommandFragment(), 1);

        return hmap;
    }

    /**
     * Pick a random command from the HashMap {@code commands}.
     * Each command has a probability weight attached to it. The higher the weight of a command,
     * the higher the chance of this command being picked. The chance of the command "example"
     * happening is:
     * {@code p("example") = commands.get("example") / getWeightSum()}
     * @return one of the classes in the HashMap {@code commands}
     */
    private Fragment getRandomCommand() {
        int weightSum = getWeightSum();
        int r = rand.nextInt(weightSum) + 1; // Pick a random number from [1, weightSum]
        System.out.println("Weight sum:  " + weightSum);
        System.out.println("Random value:" + r); // FOR DEBUGGING PURPOSES
        for (Map.Entry<Fragment, Integer> entry : commands.entrySet()) { //Loop through all commands
            r -= entry.getValue(); // Decrease the random value by this command's weight
            if (r <= 0) { // If the random number has been reduced to 0 or less...
                return entry.getKey(); // ...pick this command
            } // Otherwise move to next command
        }

        return null; //Never used (if everything works properly :D)
    }

    /**
     * Calculates the sum of all the commands their probability weights
     * @return the sum of all the values in {@code commands}
     */
    private int getWeightSum() {
        int sum = 0; // Start out with 0

        for (Integer weight : commands.values()) {
            sum += weight; // Add each command's probability weight to the sum
        }

        return sum; // Return the sum
    }

    /**
     * Updates every command's probability weight, knowing that {@code chosenCommand} was just
     * picked.
     * Every command's weight is increased by 1, except for that of {@code chosenCommand}, which
     * gets set to 1.
     * @param chosenCommand
     * @modifies commands
     */
    private void updateWeights(Fragment chosenCommand) {
        for (Map.Entry<Fragment, Integer> entry : commands.entrySet()) {
            if (entry.getKey() == chosenCommand) {
                entry.setValue(1); // Set chosen command's weight to 1
            } else {
                entry.setValue(entry.getValue() + 1); // Increase the others by 1
            }
        }
    }

    /**
     * Replaces the previous command fragment by a new one and replaces the previous time fragment
     * with a new one.
     * @param command  the chosen command to be displayed
     */
    private void displayNewCommand(Fragment command) {
        // Create new transaction
        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        // Replace the previous command with the new one
        transaction.replace(R.id.shown_screen, command);

        // Create new timer fragment and replace the old one
        timerFragment = TimerFragment.newInstance(score);
        transaction.replace(R.id.timer_location, timerFragment);

        // Commit the transaction
        transaction.commit();
    }

    /* FOR DEBUGGING PURPOSES */
    private void printWeights() {
        for (Map.Entry<Fragment, Integer> entry : commands.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
