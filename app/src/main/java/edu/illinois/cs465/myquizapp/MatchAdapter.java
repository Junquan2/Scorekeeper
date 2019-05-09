package edu.illinois.cs465.myquizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MatchAdapter extends ArrayAdapter<Match> {
    public MatchAdapter(Context context, ArrayList<Match> matches) {
        super(context, 0, matches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Match m = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        // Lookup view for data population
        TextView info = (TextView) convertView.findViewById(R.id.matchinfo);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        // Populate the data into the template view using the data object
        info.setText(m.info);
        score.setText(m.score);
        // Return the completed view to render on screen
        return convertView;
    }
}