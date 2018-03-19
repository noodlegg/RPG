package nl.tue.appdev_homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button solo = (Button) findViewById(R.id.solo);
        Button party = (Button) findViewById(R.id.party);
        Button online = (Button) findViewById(R.id.online);


        solo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mainMenu.this,
                        "solo mode", Toast.LENGTH_SHORT).show();
            }
        });
        party.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mainMenu.this,
                        "party mode", Toast.LENGTH_SHORT).show();
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mainMenu.this,
                        "online mode", Toast.LENGTH_SHORT).show();
            }
        });

        }
}
