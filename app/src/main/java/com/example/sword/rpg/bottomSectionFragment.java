package com.example.sword.rpg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class bottomSectionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.button_navigation_fragment, container, false);

        final Button mainMenu = (Button) view.findViewById(R.id.mainMenu);
        final Button settings = (Button) view.findViewById(R.id.settings);
        final Button leaderBoard = (Button) view.findViewById(R.id.leaderBoard);


        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMainMenuFragment();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchSetting();
            }
        });
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLeaderBoardFragment();
            }
        });
        return view;

    }

    public void switchSetting() {
        settingFragment fragment = new settingFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void openMainMenuFragment() {
        settingFragment fragment = new settingFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
    public void openLeaderBoardFragment() {
        settingFragment fragment = new settingFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }


}
