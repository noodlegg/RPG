package nl.tue.appdev_homepage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class mainMenu extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.mainMenuFragment, container, false);

        Button solo = (Button) view.findViewById(R.id.solo);
        Button party = (Button) view.findViewById(R.id.party);
        Button online = (Button) view.findViewById(R.id.online);


        solo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        party.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
            return view;
        }
}
