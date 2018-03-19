package com.example.sword.rpg_leaderboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sword on 19-Mar-18.
 */

public class Leaderboard extends AppCompatActivity {

    public static final String ANONYMOUS = "anonymous";
    public static final int SCORE_LIMIT = 1000;
    private ListView mListView;
    private LeaderboardAdapter mLeaderboardAdapter;
    private ProgressBar mProgressBar;
    private EditText mEditText;
    private Button mButton;
    private String mUsername;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPlayerDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mUsername = ANONYMOUS;

        // Initialize Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mPlayerDatabaseReference = mFirebaseDatabase.getReference().child("players");

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.lb_progressBar);
        mListView = (ListView) findViewById(R.id.lb_listView);
        mEditText = (EditText) findViewById(R.id.lb_editText);
        mButton = (Button) findViewById(R.id.lb_button);

        // Initialize message ListView and its adapter
        List<PlayerScore> playerScores = new ArrayList<>();
        mLeaderboardAdapter
                = new LeaderboardAdapter(this, R.layout.leaderboard_list_item, playerScores);
        mListView.setAdapter(mLeaderboardAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mButton.setEnabled(true);
                } else {
                    mButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(SCORE_LIMIT)});

        // Button sends score to Firebase and clears EditText
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerScore playerScore
                        = new PlayerScore(mUsername, Integer.parseInt(mEditText.getText().toString()));
                mPlayerDatabaseReference.push().setValue(playerScore);
                // Clear EditText
                mEditText.setText("");
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PlayerScore playerScore = dataSnapshot.getValue(PlayerScore.class);
                mLeaderboardAdapter.add(playerScore);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mPlayerDatabaseReference.addChildEventListener(mChildEventListener);
    }
}
