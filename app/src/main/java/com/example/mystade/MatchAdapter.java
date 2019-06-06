package com.example.mystade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mystade.Model.Match;

import java.util.ArrayList;

public class MatchAdapter extends ArrayAdapter<Match> {
    public MatchAdapter(Context context, ArrayList<Match> matchs) {
        super(context, 0, matchs);
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Match match = getItem(position);






        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item, null);
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView desc = (TextView) convertView.findViewById(R.id.description);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView id = (TextView) convertView.findViewById(R.id.id);

        // Populate the data into the template view using the data object
        desc.setText(match.getDescription());
        date.setText(match.getDate());
        id.setText(match.getId());
        // Return the completed view to render on screen
        return convertView;
    }
}