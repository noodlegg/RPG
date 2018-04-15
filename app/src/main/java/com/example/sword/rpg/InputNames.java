package com.example.sword.rpg;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Provides a user interface through which the names of the participating players in the next game
 * of party mode can be inputted.
 * Each name that is added, is shown in a list on screen and can be removed by pressing a button
 * next to it.
 * No more than 6 names can be entered and each one should be unique.
 */
public class InputNames extends AppCompatActivity {

    private int MAX_PLAYERS = 6; // Maximum number of players per game

    private EditText mInputField; // Name input field
    private Button mPlayButton; // "Play!" button to start the game
    private ImageButton mAddButton; // "+" button to add a player name to the list

    private ArrayList<TextView> textViews = new ArrayList<>(); // The text views for the names
    private ArrayList<ImageView> imageViews = new ArrayList<>(); // The background blocks for names
    private ArrayList<ImageButton> buttons = new ArrayList<>(); // "x" buttons to delete names
    private ArrayList<String> names = new ArrayList<>(); // All names as strings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_names);

        mInputField = findViewById(R.id.input_field); // Get input field
        mPlayButton = findViewById(R.id.play_button); // Get "Play!" button
        mAddButton = findViewById(R.id.add_name_button); // Get "+" button

        mPlayButton.setVisibility(View.INVISIBLE); // Disable the "Play!" button

        setListeners(); // Add listeners to the desired fields
    }


    /**
     * Logic for entering a new name.
     * Makes sure names are at least one character long and unique. Calls to auxiliary methods make
     * up most of the process.
     * @param name  String of the name that has to be added
     */
    private void addName(String name) {
        if (name.length() == 0) {
            return;
        }

        if (isNameInPlayerList(name)) {
            Toast.makeText(this, (name + " is already in the game!"),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        displayExtraName(name); // Display the new name in the list
        rearrangeElements(); // Rearrange all the elements so they appear on the correct location

        mInputField.setText(""); // Remove the inputted text from the input field
    }

    /**
     * Adds new views for displaying a new name and only aligns them horizontally
     * @param name  String of the name that has to be added
     */
    private void displayExtraName(String name) {
        ConstraintLayout layout = findViewById(R.id.input_names_constraint);
        ConstraintSet set = new ConstraintSet();

        TextView textView = newTextView(name); // Create TextView with the name
        ImageView textBg = newTextBg(); // Create an image view with background for the name
        ImageButton button = newButton(); // Create working delete button

        // Add the new elements to the view
        layout.addView(textBg);
        layout.addView(button);
        layout.addView(textView);

        set.clone(layout);

        int SIDE_MARGIN = (int) dpToPx(31); // Margin between elements and the sides of the screen

        // Constraint elements horizontally
        set.connect(textBg.getId(), ConstraintSet.START,
                layout.getId(), ConstraintSet.START, SIDE_MARGIN);
        set.connect(button.getId(), ConstraintSet.END,
                layout.getId(), ConstraintSet.END, SIDE_MARGIN);
        set.connect(textView.getId(), ConstraintSet.START,
                layout.getId(), ConstraintSet.START, SIDE_MARGIN + 30);

        set.applyTo(layout); // Apply the constraints to the layout
    }

    /**
     * Creates a new text view for a new name with all the correct properties to look nice.
     * @param name  String of the name that has to be added
     * @return TextView with the new name
     */
    private TextView newTextView(String name) {
        TextView textView = new TextView(this); // Create new TextView
        textView.setText(shorten(name)); // Set text to inputted name
        textView.setId(textViews.size() * 3); // Set unique ID
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18); // Set font size
        textView.setTypeface(null, Typeface.BOLD); // Make text bold
        textView.setTextColor(Color.BLACK); // Make text black

        names.add(name);
        textViews.add(textView); // Add to list of text views
        return textView;
    }

    /**
     * Creates a new image view that is used as a background field to display the new name
     * @return ImageView that is used as background field for a name
     */
    private ImageView newTextBg() {
        ImageView imageView = new ImageView(this); // Create new ImageView
        imageView.setImageResource(R.drawable.name_field); // Pick image
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // Set scale type
        imageView.setMaxWidth((int) dpToPx(230)); // Set height
        imageView.setId(imageViews.size() * 3 + 1); // Set unique ID
        // Make sure the image scales according to the width and height
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);

        imageViews.add(imageView); // Add to list of image views
        return imageView;
    }

    /**
     * Creates a new "X" button that can be pushed to remove a name
     * @return ImageButton that can be pressed to remove a name
     */
    private ImageButton newButton() {
        final ImageButton button = new ImageButton(this); // Create new ImageButton
        button.setImageResource(R.drawable.delete_name); // Pick image
        button.setBackgroundColor(Color.TRANSPARENT); // Make background transparent
        button.setMaxHeight((int) dpToPx(57)); // Set height
        button.setId(buttons.size() * 3 + 2);
        // Make sure the button scales according to the width and height
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button.setAdjustViewBounds(true);

        // Make sure the name is removed when this button gets pressed
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {removeName(button); }
        });

        buttons.add(button); // Add to list of buttons
        return button;
    }

    /**
     * Removes a name from the list and removes all the views in the ConstraintLayout that are used
     * to display it on the screen.
     * @param button the button that was pressed
     */
    private void removeName(ImageButton button) {
        int id = (int) button.getId() - 2; // Get id from button
        int index = id / 3; // Get corresponding index for ArrayLists

        TextView textView = textViews.remove(index); // Remove and store appropriate name
        ImageView imageView = imageViews.remove(index); // Remove and store appropriate name bg
        buttons.remove(index); // Remove button that was just pressed
        names.remove(index); // Remove name from list

        rearrangeElements(); // Rearrange elements vertically

        // Remove all views from the deleted name from the ConstraintLayout
        ConstraintLayout layout = findViewById(R.id.input_names_constraint);
        layout.removeView(textView);
        layout.removeView(imageView);
        layout.removeView(button);

    }

    /**
     * Update all elements and rearrange the different names vertically.
     *  - Move views for names up and down so they from a nice list
     *  - Show / hide "Play!" button
     *  - Enable / disable the input of names
     */
    private void rearrangeElements() {
        int DIFFERENCE = (int) dpToPx(50); // Distance between two names
        int TOP_SPACE = (int) dpToPx(160); // Distance first name from top of the screen

        ConstraintLayout layout = findViewById(R.id.input_names_constraint);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        int multiplier; // Used to the determine the correct location of a name

        for (int i = 0; i < textViews.size(); i++) {
            multiplier = textViews.size() - i - 1; // New names are displayed on top

            TextView textView = textViews.get(i);
            ImageView imageView = imageViews.get(i);
            ImageButton button = buttons.get(i);

            // Update the ids (may change if a name gets deleted)
            textView.setId(3 * i);
            imageView.setId(3 * i + 1);
            button.setId(3 * i + 2);

            // Constraint all elements for this specific name vertically
            set.connect(textView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP,
                    multiplier * DIFFERENCE + TOP_SPACE + (int) dpToPx(3));
            set.connect(imageView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP,
                    multiplier * DIFFERENCE + TOP_SPACE);
            set.connect(button.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP,
                    multiplier * DIFFERENCE + TOP_SPACE - (int) dpToPx(11));
        }

        set.applyTo(layout); // Apply changes

        showPlayButton(); // Show / hide "Play!" button
        disableInput(); // Enable / disable name input field
    }

    /**
     * Shows the "Play!" button if at least two names have been entered and hides it if less than
     * two names have been entered.
     */
    private void showPlayButton() {
        if (textViews.size() < 2) {
            mPlayButton.setVisibility(View.INVISIBLE); // Hide button
        } else {
            mPlayButton.setVisibility(View.VISIBLE); // Show button
        }
    }

    /**
     * Disables the input of names if the maximum numbers of players has been reached and enables
     * it otherwise.
     */
    private void disableInput() {
        if (textViews.size() >= MAX_PLAYERS) {
            // Disable keyboard (source: https://stackoverflow.com/a/1109108/9499278)
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            mInputField.setFocusable(false); // Disable input field
            mInputField.setHint("Max. " + MAX_PLAYERS + " players per game"); // Change input hint
        } else {
            mInputField.setFocusableInTouchMode(true); // Enable input field
            mInputField.setHint("Add players"); // Change input hint
        }
    }

    /**
     * Starts a new Party Game with the inputted names.
     */
    private void startGame() {
        ArrayList<PlayerTriplet> players = makePlayerList(); // Store players in the correct format
        Intent intent = new Intent(this, PartyGame.class);
        intent.putExtra("players", players);
        startActivity(intent);
    }

    /**
     * Returns an ArrayList with all participating players as PlayerTriplets with the correct names
     * and each with still the maximum number of commands left and a score of 0.
     * @return  ArrayList of all participating players as <name, #commands, 0,>
     */
    private ArrayList<PlayerTriplet> makePlayerList() {
        ArrayList<PlayerTriplet> players = new ArrayList<>();
        for (String name : names) {
            PlayerTriplet player = new PlayerTriplet(name);
            players.add(player);
        }
        return players;
    }

    /**
     * Set listeners for appropriate views.
     *  - Check if the text in the input field was changed
     *  - Check if the button was pressed to add a new name
     *  - Check if the "Play!" button was pressed
     */
    private void setListeners() {
        // Add listener to show/hide the "add" button when the input changes
        mInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mInputField.getText().length() > 0) { // If there is text in the input field:
                    mAddButton.setVisibility(View.VISIBLE); // Show the "add" button
                } else { // Otherwise (if there is no text in the input field):
                    mAddButton.setVisibility(View.INVISIBLE); // Hide the "add" button
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Add listener to add names when the "add" button is pressed
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addName(mInputField.getText().toString());
            }
        });

        // Add listener to start a new party game when the "Play!" button is pressed
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    /**
     * Checks whether a name is already in the list of names.
     * @param name  the name that is going to be checked
     * @return whether {@code name} is in {@code names}
     */
    private boolean isNameInPlayerList(String name) {
        if (names.contains(name)) {
            return true;
        }

        return false;
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
     * Converts a dp measurement to the corresponding measurement in pixels
     * @param dp  measurement in dp
     * @return  {@code dp} converted to pixels
     */
    private float dpToPx(float dp) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
