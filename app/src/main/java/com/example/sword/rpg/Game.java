package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main logic for running a game.
 * The user is presented with short task that they have to fulfill in a set amount of time: a
 * command. If they fulfill this command within the time limit, they receive a point.
 * When the game ends can vary between the different game modes. Currently there are two different
 * game modes: solo mode and party mode.
 */
public class Game extends AppCompatActivity{
    protected ArrayList<CommandTriplet> commands = initializeCommands(); // All possible commands
    protected int score = 0; // Current score (Solo mode)

    protected boolean isInverted = false; // Whether the current command is inverted or not
    protected Random rand = new Random(); // For random processes

    protected TimerFragment timerFragment; // The Fragment of the timer progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        doNewCommand(); // Proceed to game logic
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
        if (success == !isInverted) {
            showCorrectScreen();
        } else {
            System.out.println("Score: " + score); // FOR TESTING PURPOSES
            // Go to the Game Over screen
            Intent intent = new Intent(this, GameOverSolo.class);
            Bundle bundle = new Bundle(); // New Bundle to pass score to Game Over screen
            bundle.putInt("score", score); // Add score to bundle
            intent.putExtras(bundle); // Add bundle to intent
            startActivity(intent); // Start the intent
            finish();
        }
    }

    /**
     * Overall logic for giving a new command:
     * 1. Pick random command.
     * 2. Update each command's weight accordingly.
     * 3. Display new command.
     */
    public void doNewCommand() {
        CommandTriplet command = getRandomCommand(); //Pick a random command

        /* FOR DEBUGGING PURPOSES */
        System.out.println("=====================================");
        System.out.println("Old weights:");
        printWeights();

        updateCommandWeights(command); // Update the probability weights from the commands

        /* FOR DEBUGGING PURPOSES */
        System.out.println("New weights:");
        printWeights(); // FOR DEBUGGING PURPOSES

        isInverted = shouldInvert(); // Decide whether this command should be inverted or not

        displayNewCommand(command);
    }

    /**
     * Creates an ArrayList with every possible command in there, with weight 1 and each their own
     * difficulty.
     * @return ArrayList with every possible command, with weight 1 and each their own difficulty
     */
    private ArrayList<CommandTriplet> initializeCommands() {
        // ArrayList with all the commands
        ArrayList<CommandTriplet> commands = new ArrayList<>();

        // Initial weight of every command
        final int START_WEIGHT = 1;

        // Add commands with their weight and difficulty to the ArrayList
        //commands.add(new CommandTriplet(new GreenTestFragment(), START_WEIGHT, 1D));
        //commands.add(new CommandTriplet(new RedTestFragment(), START_WEIGHT, 1D));
        //commands.add(new CommandTriplet(new BlueTestFragment(), START_WEIGHT, 1D));
        //commands.add(new CommandTriplet(new SwipeCommandFragment(), START_WEIGHT, 1D));
        commands.add(new CommandTriplet(new CompassCommandFragment(), START_WEIGHT, 1D));
        //commands.add(new CommandTriplet(new ShakeCommandFragment(), START_WEIGHT, 1D));
        commands.add(new CommandTriplet(new TapCommandFragment(), START_WEIGHT, 1D));
        //commands.add(new CommandTriplet(new TiltCommandFragment(), START_WEIGHT, 1D));

        return commands;
    }

    /**
     * Picks a random command from the ArrayList {@code commands}.
     * Each command has a probability weight attached to it. The higher the weight of a command,
     * the higher the chance of this command being picked. The chance of the command "example"
     * happening is:
     * {@code p(example) = example.getWeight() / getWeightSum()}
     * @return one of the commands in the ArrayList {@code commands}
     */
    private CommandTriplet getRandomCommand() {
        int weightSum = getWeightSum(); // Get sum of all weights
        int r = rand.nextInt(weightSum) + 1; // Pick a random number from [1, weightSum]
        System.out.println("Weight sum:  " + weightSum);
        System.out.println("Random value:" + r); // FOR DEBUGGING PURPOSES
        for (CommandTriplet entry : commands) { //Loop through all commands
            r -= entry.getWeight(); // Decrease the random value by this command's weight
            if (r <= 0) { // If the random number has been reduced to 0 or less...
                return entry; // ...pick this command
            } // Otherwise move to next command
        }

        return null; //Never used (if everything works properly :D)
    }

    /**
     * Decides whether the next command should be inverted or not.
     * When a command is "inverted", you can only succeed it by NOT completing it correctly in time.
     * @return whether the next command should be inverted or not
     */
    private boolean shouldInvert() {
        int pInvert = 3; // Chance of n/10 that the command should be inverted
        Random rand = new Random();

        // Whether the command should be inverted
        return (rand.nextInt(10) < pInvert);
    }

    /**
     * Calculates the sum of all the commands their probability weights
     * @return the sum of all the weights in {@code commands}
     */
    private int getWeightSum() {
        int sum = 0; // Start out with 0

        for (CommandTriplet entry : commands) {
            sum +=  entry.getWeight(); // Add each command's probability weight to the sum
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
    private void updateCommandWeights(CommandTriplet chosenCommand) {
        for (CommandTriplet entry : commands) {
            if (entry == chosenCommand) {
                entry.resetWeight(); // Set chosen command's weight to 1
            } else {
                entry.incrementWeight(); // Increase the others by 1
            }
        }
    }

    /**
     * Replaces the previous command fragment by a new one and replaces the previous time fragment
     * with a new one.
     * @param command  the chosen command to be displayed
     */
    protected void displayNewCommand(CommandTriplet command) {
        // Create new transaction
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        // Replace the previous command with the new one
        transaction.replace(R.id.shown_screen, command.getCommand());

        // Create new timer fragment and replace the old one
        timerFragment = TimerFragment.newInstance(score, command.getDifficulty());
        transaction.replace(R.id.timer_location, timerFragment);

        // Commit the transaction for the command fragment
        transaction.commit();
    }

    public void showCorrectScreen() {
        // Create new transaction
        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        // Replace the previous command with the new one
        transaction.replace(R.id.shown_screen, new CorrectFragment());
        // Replace timer with empty fragment
        transaction.replace(R.id.timer_location, new EmptyFragment());
        transaction.commit();
    }

    public void showIncorrectScreen() {
        // Create new transaction
        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        // Replace the previous command with the new one
        transaction.replace(R.id.shown_screen, new IncorrectFragment());
        // Replace timer with empty fragment
        transaction.replace(R.id.timer_location, new EmptyFragment());
        transaction.commit();
    }

    /* FOR DEBUGGING PURPOSES */
    private void printWeights() {
        for (CommandTriplet entry : commands) {
            System.out.println("  " + entry.getCommand() + ": " + entry.getWeight());
        }
    }
}
