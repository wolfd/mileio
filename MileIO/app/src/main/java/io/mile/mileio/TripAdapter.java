package io.mile.mileio;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private static final SimpleDateFormat whenFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private final ArrayList<Trip> trips;

    public TripAdapter(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);

        return new TripAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TripAdapter.ViewHolder holder, int position) {
        // Lookup view for data population
        Trip trip = trips.get(position);

        holder.distanceView.setText(
                String.format(Locale.US, "%.1f miles", trip.getDistance())
        );

        try {
            holder.operatorNameView.setText(
                    trip.getDriver().getDisplayName()
            );
        } catch (NullPointerException exc) {
            // no user logged in yet
        }

        holder.whenView.setText(
                whenFormat.format(trip.getWhen())
        );

        // update map
        holder.setTripData(trip);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        if (holder.googleMap != null) {
            holder.googleMap.clear();
            holder.googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        private final int trackColor;
        public TextView operatorNameView;
        public TextView distanceView;
        public TextView whenView;
        public MapView mapView;
        public GoogleMap googleMap;

        private Trip tripData;

        public ViewHolder(View convertView) {
            super(convertView);

            operatorNameView = (TextView) convertView.findViewById(R.id.operator_name_view);
            distanceView = (TextView) convertView.findViewById(R.id.distance_view);
            whenView = (TextView) convertView.findViewById(R.id.when_view);

            mapView = (MapView) convertView.findViewById(R.id.map_view);

            if (mapView != null) {
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);
            }

            trackColor = ContextCompat.getColor(convertView.getContext(), R.color.colorPrimaryDark);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            this.googleMap = googleMap;
            googleMap.getUiSettings().setAllGesturesEnabled(false);

            updateMap();
        }

        public void updateMap() {
            if (googleMap == null) {
                return;
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            PolylineOptions tripTrack = new PolylineOptions();

            tripTrack.color(trackColor);

            Random random = new Random();
            // fake trip data
            for (int i = 0; i < 10; i++) {
                tripTrack.add(new LatLng(42.291358 + random.nextDouble(), -71.265296 + random.nextDouble()));
            }

            googleMap.addPolyline(tripTrack);

            LatLngBounds.Builder boundBuilder = LatLngBounds.builder();

            for (LatLng point : tripTrack.getPoints()) {
                boundBuilder = boundBuilder.include(point);
            }

            LatLngBounds bounds = boundBuilder.build();

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 32));
        }

        public void setTripData(Trip tripData) {
            this.tripData = tripData;

            updateMap();
        }
    }
}
