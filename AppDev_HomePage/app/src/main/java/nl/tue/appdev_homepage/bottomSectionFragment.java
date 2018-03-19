package nl.tue.appdev_homepage;

import android.content.Intent;
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

        final Button mainMenu = (Button) view.findViewById(R.id.mainMenu);
        final Button settings = (Button) view.findViewById(R.id.settings);
        final Button leaderBoard = (Button) view.findViewById(R.id.leaderBoard);


        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMainMenuActivity();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSettingsActivity();
            }
        });
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLeaderBoardActivity();
            }
        });
        return view;

    }

    public void openSettingsActivity() {
    }
    public void openMainMenuActivity() {
        Intent intent = new Intent(getActivity(), mainMenu.class);
        startActivity(intent);
    }
    public void openLeaderBoardActivity() {
        Intent intent = new Intent(getActivity(), leaderBoard.class);
        startActivity(intent);
    }
}
