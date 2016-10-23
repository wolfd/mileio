package io.mile.mileio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.Locale;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private final ArrayList<Trip> trips;

    public TripAdapter(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView operatorNameView;
        public TextView distanceView;
        public MapView mapView;

        public ViewHolder(View convertView) {
            super(convertView);

            operatorNameView = (TextView) convertView.findViewById(R.id.operator_name_view);
            distanceView = (TextView) convertView.findViewById(R.id.distance_view);
            mapView = (MapView) convertView.findViewById(R.id.map_view);
        }
    }

    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);

        // set the view's size, margins, paddings and layout parameters
        // ...

        TripAdapter.ViewHolder vh = new TripAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(TripAdapter.ViewHolder holder, int position) {
        // Lookup view for data population
        Trip trip = trips.get(position);

        holder.distanceView.setText(
                String.format(Locale.US, "%.1f miles", trip.distance)
        );

        holder.operatorNameView.setText(
                trip.driver.getDisplayName()
        );
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
}
