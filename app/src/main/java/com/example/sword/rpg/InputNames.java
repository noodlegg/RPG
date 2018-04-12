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
import android.text.Selection;
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

public class InputNames extends AppCompatActivity {

    private int MAX_PLAYERS = 6;

    private EditText mInputField;
    private Button mPlayButton;
    private ImageButton mAddButton;

    private ArrayList<TextView> textViews = new ArrayList<>();
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<ImageButton> buttons = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_names);

        mInputField = findViewById(R.id.input_field);
        mPlayButton = findViewById(R.id.play_button);
        mAddButton = findViewById(R.id.add_name_button);

        System.out.println("EditText id: " + findViewById(R.id.input_field).getId());
        System.out.println("Text bg: " + findViewById(R.id.input_background).getId());

        mPlayButton.setVisibility(View.INVISIBLE);

        setListeners();
    }

    private void addName(String name) {
        if (name.length() == 0) {
            return;
        }

        if (isNameInPlayerList(name)) {
            Toast.makeText(this, (name + " is already in the game!"),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        displayExtraName(name);
        rearrangeElements();

        mInputField.setText("");
    }

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

        int SIDE_MARGIN = (int) dpToPx(31);

        // Constraint elements horizontally
        set.connect(textBg.getId(), ConstraintSet.START,
                layout.getId(), ConstraintSet.START, SIDE_MARGIN);
        set.connect(button.getId(), ConstraintSet.END,
                layout.getId(), ConstraintSet.END, SIDE_MARGIN);
        set.connect(textView.getId(), ConstraintSet.START,
                layout.getId(), ConstraintSet.START, SIDE_MARGIN + 30);

        set.applyTo(layout); // Apply the constraints to the layout
    }

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

    private ImageButton newButton() {
        final ImageButton button = new ImageButton(this); // Create new ImageButton
        button.setImageResource(R.drawable.delete_name); // Pick image
        button.setBackgroundColor(Color.TRANSPARENT); // Make background transparent
        button.setMaxHeight((int) dpToPx(57)); // Set height
        button.setId(buttons.size() * 3 + 2);
        // Make sure the button scales according to the width and height
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button.setAdjustViewBounds(true);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {removeName(button); }
        });

        buttons.add(button); // Add to list of buttons
        return button;
    }

    private void removeName(ImageButton button) {
        int id = (int) button.getId() - 2; // Get id from button
        int index = id / 3; // Get corresponding index for ArrayLists
        System.out.println("id = " + id);
        System.out.println("index = " + index);

        TextView textView = textViews.remove(index);
        ImageView imageView = imageViews.remove(index);
        buttons.remove(index);
        names.remove(index);

        System.out.println("index variable: " + index);
        System.out.println("textView index: " + textViews.indexOf(textView));
        System.out.println("imageView index: " + imageViews.indexOf(imageView));
        System.out.println("button index: " + buttons.indexOf(button));

        rearrangeElements();

        ConstraintLayout layout = findViewById(R.id.input_names_constraint);
        layout.removeView(textView);
        layout.removeView(imageView);
        layout.removeView(button);

    }

    private void rearrangeElements() {
        int DIFFERENCE = (int) dpToPx(50);
        int TOP_SPACE = (int) dpToPx(160);

        ConstraintLayout layout = findViewById(R.id.input_names_constraint);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        int multiplier;

        for (int i = 0; i < textViews.size(); i++) {
            multiplier = textViews.size() - i - 1;

            TextView textView = textViews.get(i);
            ImageView imageView = imageViews.get(i);
            ImageButton button = buttons.get(i);

            // Update the ids (can change if a name gets deleted)
            textView.setId(3 * i);
            imageView.setId(3 * i + 1);
            button.setId(3 * i + 2);

            set.connect(textView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP,
                    multiplier * DIFFERENCE + TOP_SPACE + (int) dpToPx(3));
            set.connect(imageView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP,
                    multiplier * DIFFERENCE + TOP_SPACE);
            set.connect(button.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP,
                    multiplier * DIFFERENCE + TOP_SPACE - (int) dpToPx(11));
        }

        set.applyTo(layout);

        showPlayButton();
        disableInput();
    }

    private void showPlayButton() {
        if (textViews.size() < 2) {
            mPlayButton.setVisibility(View.INVISIBLE);
        } else {
            mPlayButton.setVisibility(View.VISIBLE);
        }
    }

    private void disableInput() {
        if (textViews.size() >= MAX_PLAYERS) {
            // Disable keyboard (source: https://stackoverflow.com/a/1109108/9499278)
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            mInputField.setFocusable(false);
            mInputField.setHint("Max. " + MAX_PLAYERS + " players per game");
        } else {
            mInputField.setFocusableInTouchMode(true);
            mInputField.setHint("Add players");
        }
    }

    private void startGame() {
        ArrayList<PlayerTriplet> players = makePlayerList();
        Intent intent = new Intent(this, PartyGame.class);
        intent.putExtra("players", players);
        startActivity(intent);
    }

    private ArrayList<PlayerTriplet> makePlayerList() {
        ArrayList<PlayerTriplet> players = new ArrayList<>();
        for (String name : names) {
            PlayerTriplet player = new PlayerTriplet(name, 3, 0);
            players.add(player);
        }
        return players;
    }

    private void setListeners() {
        mInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mInputField.getText().length() > 0) {
                    mAddButton.setVisibility(View.VISIBLE);
                } else {
                    mAddButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addName(mInputField.getText().toString());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private boolean isNameInPlayerList(String name) {
        if (names.contains(name)) {
            return true;
        }

        return false;
    }

    private String shorten(String name) {
        if (name.length() <= 13) {
            return name;
        } else {
            return name.substring(0, 12) + "...";
        }
    }

    private float dpToPx(float dp) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
