package com.example.sword.rpg;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores a triplet of a String and two integers
 */
public class PlayerTriplet implements Parcelable{

    private final String name; // Name of the player
    private Integer commandsLeft; // Number of commands that are still to be given to this player
    private Integer score; // Score of the player

    public PlayerTriplet(String name, int commandsLeft, int score) {
        this.name = name;
        this.commandsLeft = commandsLeft;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Integer getCommandsLeft() {
        return commandsLeft;
    }

    public Integer getScore() {
        return score;
    }

    /**
     * Decreases this.commandsLeft by one and increments the score if the command was executed
     * successfully.
     * @param success  whether the command was executed successfully.
     */
    public void commandExecuted(boolean success) {
        commandsLeft--;
        if (success) {
            score ++;
        }
    }

    /* All code below is to make sure that PlayerTriplet implements Parcelable correctly.
     * This allows PlayerTriplets to be passed as extras in Intents.
     * (Source code skeleton: https://stackoverflow.com/a/7181792/9499278)
     */

    public PlayerTriplet(Parcel in) {
        this.name = in.readString();
        this.commandsLeft = in.readInt();
        this.score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(commandsLeft);
        out.writeInt(score);
    }

    public static final Parcelable.Creator<PlayerTriplet> CREATOR = new Parcelable.Creator<PlayerTriplet>() {
        public PlayerTriplet createFromParcel(Parcel in) {
            return new PlayerTriplet(in);
        }

        public PlayerTriplet[] newArray(int size) {
            return new PlayerTriplet[size];
        }
    };
}
