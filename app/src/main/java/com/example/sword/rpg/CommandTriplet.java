package com.example.sword.rpg;

/**
 * Stores a triplet of a Fragment, Integer and Double
 */
public class CommandTriplet {

    private final Command command;
    private Integer weight;
    private final Double difficulty;

    public CommandTriplet(Command command, Integer weight, Double difficulty) {
        this.command = command;
        this.weight = weight;
        this.difficulty = difficulty;
    }

    public Command getCommand() {
        return command;
    }

    public Integer getWeight() {
        return weight;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void incrementWeight() {
        weight++;
    }

    public void resetWeight() {
        weight = 1;
    }
}
