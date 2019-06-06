package com.example.mystade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.mystade.Model.Reservation;

import java.util.ArrayList;

public class ListAdapterReservation extends ArrayAdapter<Reservation> {
    public ListAdapterReservation(Context context, ArrayList<Reservation> reservations) {
        super(context, 0, reservations);
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Reservation reservation = getItem(position);






        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row_reservation, null);
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView placeId = (TextView) convertView.findViewById(R.id.placeId);

        // Populate the data into the template view using the data object
        description.setText(reservation.getDescription());
        date.setText(reservation.getDate());
       placeId.setText("Place num : "+reservation.getPlaceId());
        id.setText(reservation.getId());
        // Return the completed view to render on screen
        return convertView;
    }
}