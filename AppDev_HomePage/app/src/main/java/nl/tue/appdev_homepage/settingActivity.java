package nl.tue.appdev_homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.view.View;

public class settingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final Button howToPlayButton = (Button) findViewById(R.id.howToPlay);
        final Button aboutUsButton = (Button) findViewById(R.id.aboutUs);

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

    }

    public void openAboutUsActivity() {
        Intent intent = new Intent(this, aboutUs.class);
        startActivity(intent);
    }
    public void openHowToPlayActivity() {
        Intent intent = new Intent(this, howToPlay.class);
        startActivity(intent);
    }
}
