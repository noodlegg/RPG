package com.example.sword.rpg_leaderboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
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

        TextView playerNameTextView = (TextView) convertView.findViewById(R.id.lb_playerName);
        TextView playerScoreTextView = (TextView) convertView.findViewById(R.id.lb_playerScore);
        TextView playerRankTextView = (TextView) convertView.findViewById(R.id.lb_ranking);

        playerRankTextView.setText(String.valueOf(position+1));
        PlayerScore playerScore = getItem(position);
        playerNameTextView.setText(playerScore.getName());
        playerScoreTextView.setText(String.valueOf(playerScore.getScore()));


        return convertView;
    }
}
