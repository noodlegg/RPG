package nl.tue.appdev_homepage;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.view.View;

public class settingFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.settingFragment, container, false);

        final Button howToPlayButton = (Button) view.findViewById(R.id.howToPlay);
        final Button aboutUsButton = (Button) view.findViewById(R.id.aboutUs);

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
