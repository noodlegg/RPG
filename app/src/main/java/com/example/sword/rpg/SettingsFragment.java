package com.example.sword.rpg;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class SettingsFragment extends Fragment {

    private Button howToPlayButton;
    private Button aboutUsButton;
    private EditText userNameInput;
    private Button confirmUserName;
    private TextView usernameDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        howToPlayButton = view.findViewById(R.id.howToPlay);
        aboutUsButton = view.findViewById(R.id.aboutUs);
        userNameInput = (EditText) view.findViewById(R.id.username_input);
        confirmUserName = (Button) view.findViewById(R.id.confirm_username);
        usernameDisplay = (TextView) view.findViewById(R.id.username);

        usernameDisplay.setText(((Home) getActivity()).getUsername());

        confirmUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserName = userNameInput.getText().toString();
                usernameDisplay.setText(newUserName);
                ((Home) getActivity()).setUsername(newUserName);
                userNameInput.setText("");
            }
        });

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