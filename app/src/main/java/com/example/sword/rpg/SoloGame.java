package com.example.sword.rpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SoloGame extends AppCompatActivity {
    private HashMap<Class, Integer> commands = initializeCommands();

    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_game);
        onResume(); //Proceed to game logic
    }

    @Override
    protected void onResume() {
        super.onResume();
        Class command = getRandomCommand(); //Pick a random command

        /* FOR DEBUGGING PURPOSES */
        System.out.println("=====================================");
        System.out.println("Old weights:");
        printWeights();

        updateWeights(command); //Update the probability weights from the commands

        /* FOR DEBUGGING PURPOSES */
        System.out.println("New weights:");
        printWeights(); //FOR DEBUGGING PURPOSES

        Intent intent = new Intent(this, command);
        startActivity(intent); //Start the randomly chosen command
    }

    /**
     * Creates a HashMap with every possible command in there, each with probability weight 1.
     * @return a HashMap with every possible command, each with probability weight 1.
     */
    private HashMap<Class, Integer> initializeCommands() {
        HashMap<Class, Integer> hmap = new HashMap<>();
        hmap.put(SwipeMechanic.class, 1);
        hmap.put(ShakeMechanic.class, 1);
        hmap.put(CompassMechanic.class, 1);
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
    private Class getRandomCommand() {
        int weightSum = getWeightSum();
        int r = rand.nextInt(weightSum) + 1; //Pick a random number from [1, weightSum]
        System.out.println("Weight sum:  " + weightSum);
        System.out.println("Random value:" + r); //FOR DEBUGGING PURPOSES
        for (Map.Entry<Class, Integer> entry : commands.entrySet()) { //Loop through all commands
            r -= entry.getValue(); //Decrease the random value by this command's weight
            if (r <= 0) { //If the random number has been reduced to 0 or less...
                return entry.getKey(); //...pick this command
            } //Otherwise move to next command
        }

        return null; //Never used (if everything works properly)
    }

    /**
     * Calculates the sum of all the commands their probability weights
     * @return the sum of all the values in {@code commands}
     */
    private int getWeightSum() {
        int sum = 0; //Start out with 0

        for (Integer weight : commands.values()) {
            sum += weight; //Add each command's probability weight to the sum
        }

        return sum; //Return the sum
    }

    /**
     * Updates every command's probability weight, knowing that {@code chosenCommand} was just
     * picked.
     * Every command's weight is increased by 1, except for that of {@code chosenCommand}, which
     * gets set to 1.
     * @param chosenCommand
     * @modifies commands
     */
    private void updateWeights(Class chosenCommand) {
        for (Map.Entry<Class, Integer> entry : commands.entrySet()) {
            if (entry.getKey() == chosenCommand) {
                entry.setValue(1); //Set chosen command's weight to 1
            } else {
                entry.setValue(entry.getValue() + 1); //Increase the others by 1
            }
        }
    }

    /* FOR DEBUGGING PURPOSES */
    private void printWeights() {
        for (Map.Entry<Class, Integer> entry : commands.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
