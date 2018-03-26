package com.example.sword.rpg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class bottomSectionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.button_navigation_fragment, container, false);



        final Button mainMenu =  view.findViewById(R.id.mainMenu);
        final Button settings =  view.findViewById(R.id.settings);
        final Button leaderBoard = view.findViewById(R.id.leaderBoard);

        switchFragment(new mainMenu());

        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(new mainMenu());

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(new settingFragment());
            }
        });
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(new leaderBoard());

            }
        });
        return view;

    }

    public void switchFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



}
