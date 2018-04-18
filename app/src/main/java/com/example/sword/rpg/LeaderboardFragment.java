package com.example.sword.rpg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    public static final String ANONYMOUS = "anonymous";
    public static final int SCORE_LIMIT = 1000;
    private ListView mListView;
    private LeaderboardAdapter mLeaderboardAdapter;
    private EditText mEditText;
    private Button mButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPlayerDatabaseReference;
    private ChildEventListener mChildEventListener;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        // Initialize Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mPlayerDatabaseReference = mFirebaseDatabase.getReference().child("players");

        // Initialize references to views
        mListView = (ListView) rootView.findViewById(R.id.lb_listView);
        mEditText = (EditText) rootView.findViewById(R.id.lb_editText);
        mButton = (Button) rootView.findViewById(R.id.lb_button);

        // Initialize ListView and its adapter
        List<PlayerScore> playerScores = new ArrayList<>();

        // Get Highscore from GameOver
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("highScore")) {
            Bundle bundle = getActivity().getIntent().getExtras();
            int score = bundle.getInt("highScore");
            String username = ((Home) getActivity()).getUsername();
            PlayerScore playerScore = new PlayerScore(username, score);
            mPlayerDatabaseReference.child(username).setValue(playerScore);
        }

        mLeaderboardAdapter
                = new LeaderboardAdapter(getActivity(), R.layout.leaderboard_list_item, playerScores);
        mLeaderboardAdapter.notifyDataSetChanged();
        mListView.setAdapter(mLeaderboardAdapter);

        // EditText for input
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
                        = new PlayerScore("tester", Integer.parseInt(mEditText.getText().toString()));
                mPlayerDatabaseReference.child("manual").setValue(playerScore);
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

        return rootView;
    }

}
