package com.example.sword.rpg;

import android.support.annotation.NonNull;
import java.util.Comparator;

/**
 * Created by sword on 19-Mar-18.
 */

public class PlayerScore {

    private String name;
    private int score;

    public PlayerScore() {
    }

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

}