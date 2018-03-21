package com.example.sword.rpg_leaderboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sword on 19-Mar-18.
 */

public class LeaderboardAdapter extends ArrayAdapter<PlayerScore> {
    public LeaderboardAdapter(Context context, int resource, List<PlayerScore> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(R.layout.leaderboard_list_item, parent, false);
        }

        // Initialize references to views
        TextView playerNameTextView = (TextView) convertView.findViewById(R.id.lb_playerName);
        TextView playerScoreTextView = (TextView) convertView.findViewById(R.id.lb_playerScore);
        TextView playerRankTextView = (TextView) convertView.findViewById(R.id.lb_ranking);

        // Fill the TextViews
        playerRankTextView.setText(String.valueOf(position+1));
        PlayerScore playerScore = getItem(position);
        playerNameTextView.setText(playerScore.getName());
        playerScoreTextView.setText(String.valueOf(playerScore.getScore()));


        return convertView;
    }

    // Sorts the scores in descending order
    @Override
    public void notifyDataSetChanged() {
        this.setNotifyOnChange(false);
        this.sort(new Comparator<PlayerScore>() {

            @Override
            public int compare(PlayerScore ps1, PlayerScore ps2) {
                return ps2.getScore() - ps1.getScore();
            }
        });

        super.notifyDataSetChanged();
        this.setNotifyOnChange(true);
    }
}
