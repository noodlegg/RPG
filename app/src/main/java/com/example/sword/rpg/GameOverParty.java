package com.example.sword.rpg;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Game Over screen that is shown at the end of a Party Game.
 * It contains a list of all the participating players and their score. The list is sorted in
 * descending order, based on the scores of the players.
 */
public class GameOverParty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_party);

        // Fetch list of players from previous activity
        final ArrayList<PlayerTriplet> players =
                (ArrayList) getIntent().getSerializableExtra("players");

        Collections.sort(players); // Sort players in descending order based on their score
        displayResult(players); // Display the names and their scores to the user

        // The Home button
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {toHome(); }
        });

        // The Rematch button
        Button retryButton = findViewById(R.id.rematch_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {playAgain(players); }
        });

    }

    /**
     * Logic for displaying the result of the game.
     * @param players  participating players
     */
    private void displayResult(ArrayList<PlayerTriplet> players) {
        for (int i = 0; i < players.size(); i++) {
            PlayerTriplet player = players.get(i);

            int nameId = 3 * i; // Unique id for the name
            int scoreId = 3 * i + 1; // Unique id for the score

            // Add TextViews to the layout
            addTextView(player.getName(), nameId);
            addTextView(player.getScore().toString(), scoreId);
        }
    }

    /**
     * Adds another TextView to the layout.
     * @param text  text for this TextView
     * @param id  unique id for this TextView
     * @return
     */
    private TextView addTextView(String text, int id) {
        TextView textView = new TextView(this); // Create new TextView
        textView.setText(shorten(text)); // Set text to inputted name
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18); // Set font size
        textView.setTypeface(null, Typeface.BOLD); // Make text bold
        textView.setTextColor(Color.BLACK); // Make text black
        textView.setId(id); // Set (unique) id

        constraintHorizontally(textView, id % 3);
        constraintVertically(textView, id / 3);

        return textView;
    }

    /**
     * Constraints a text view to the side of the screen.
     * @param textView  the TextView that has to be constrained
     * @param type  what the content of the TextView is (0 for name, 1 for score)
     */
    private void constraintHorizontally(TextView textView, int type) {
        int SIDE_MARGIN = (int) dpToPx(80); // Margin between elements and the sides of the screen

        ConstraintLayout layout = findViewById(R.id.game_over_party_constraint);
        ConstraintSet set = new ConstraintSet();
        layout.addView(textView); // Add TextView to the layout
        set.clone(layout);

        if (type == 0) { // If it is a name:
            // Constraint it to the left side of the screen
            set.connect(textView.getId(), ConstraintSet.START,
                    layout.getId(), ConstraintSet.START, SIDE_MARGIN);
        } else if (type == 1) { // If it is a score:
            // Constraint it to the right side of the screen
            set.connect(textView.getId(), ConstraintSet.END,
                    layout.getId(), ConstraintSet.END, SIDE_MARGIN);
        }

        set.applyTo(layout);
    }

    /**
     * Constraints a TextView to the top of the screen, so it is displayed ad the correct height.
     * @param textView  TextView that has to be constrained
     * @param i  index of occurrence in the player list
     */
    private void constraintVertically(TextView textView, int i) {
        int TOP_SPACE = (int) dpToPx(110); // Distance between the first name and top of the screen
        int DIFFERENCE = (int) dpToPx(45); // Distance between two names

        int margin = TOP_SPACE + i * DIFFERENCE; // Calculate the correct margin

        ConstraintLayout layout = findViewById(R.id.game_over_party_constraint);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        // Constraint TextView
        set.connect(textView.getId(), ConstraintSet.TOP,
                layout.getId(), ConstraintSet.TOP, margin);

        set.applyTo(layout); // Apply changes
    }

    /**
     * Converts a dp measurement to the corresponding measurement in pixels
     * @param dp  measurement in dp
     * @return  {@code dp} converted to pixels
     */
    private float dpToPx(float dp) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    /**
     * Cuts off a string if it is deemed too long (in this case: more than 13 characters).
     * @param name  string that (possibly) needs to be shortened
     * @return {@code name} if {@code name.length() <= 13}
     *          the first 12 characters of {@code name} followed by three dots otherwise
     */
    private String shorten(String name) {
        int MAX_LENGTH = 13;
        if (name.length() <= MAX_LENGTH) {
            return name;
        } else {
            return name.substring(0, MAX_LENGTH - 1) + "...";
        }
    }

    /**
     * Opens the Home screen.
     */
    protected void toHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    /**
     * Starts a new party game with the same players
     */
    protected void playAgain(ArrayList<PlayerTriplet> players) {
        for (PlayerTriplet player : players) {
            player.resetPlayer();
            player.resetPlayer();
        }

        Intent intent = new Intent(this, PartyGame.class);
        intent.putExtra("players", players);
        startActivity(intent);
    }
}
