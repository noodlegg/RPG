package com.example.sword.rpg;

import android.support.v4.app.Fragment;

/**
 * Stores a triplet of a Fragment, Integer and Double
 */
public class Triplet<T, U, V> {

    private final Command command;
    private Integer weight;
    private final Double difficulty;

    public Triplet(Command command, Integer weight, Double difficulty) {
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
        weight = 0;
    }
}
