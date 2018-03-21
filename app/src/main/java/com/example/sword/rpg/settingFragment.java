package com.example.sword.rpg;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;

public class settingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.setting_fragment, container, false);

        final Button howToPlayButton =  view.findViewById(R.id.howToPlay);
        final Button aboutUsButton =    view.findViewById(R.id.aboutUs);

        howToPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openHowToPlayActivity();
            }
        });
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAboutUsActivity();

            }
        });

        return view;
    }

    public void openAboutUsActivity() {
        Intent intent = new Intent(getActivity(), aboutUs.class);
        startActivity(intent);
    }
    public void openHowToPlayActivity() {
        Intent intent = new Intent(getActivity(), howToPlay.class);
        startActivity(intent);
    }
}
