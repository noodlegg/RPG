package com.example.sword.rpg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class GameSelectFragment extends Fragment {

    public GameSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_select, container, false);

        // Initialize buttons
        ImageButton playSolo = (ImageButton) rootView.findViewById(R.id.solo_mode);
        ImageButton playParty = (ImageButton) rootView.findViewById(R.id.party_mode);
        ImageButton playOnline = (ImageButton) rootView.findViewById(R.id.online_mode);

        // Opens SoloGame activity
        playSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SoloGame.class);
                startActivity(intent);
            }
        });
        // Opens PartyGame activity
        playParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShakeMechanic.class);
                startActivity(intent);
            }
        });
        // Opens MultiplayerGame activity
        playOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MultiplayerConnection.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
