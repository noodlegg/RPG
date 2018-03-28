package com.example.sword.rpg;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;


public class SettingsFragment extends Fragment {

    @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_settings, container, false);


            final Button howToPlayButton =  view.findViewById(R.id.howToPlay);
            final Button aboutUsButton =    view.findViewById(R.id.aboutUs);

            howToPlayButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        }
                    });
            aboutUsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                }
             });
                return view;
            }

 }
