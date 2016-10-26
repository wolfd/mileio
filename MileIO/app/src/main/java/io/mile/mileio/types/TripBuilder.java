package io.mile.mileio.types;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

import io.mile.mileio.data.FirebaseHandler;

public class TripBuilder {
    private Date whenStarted;
    private Date whenEnded;

    private ArrayList<Location> path;

    private FirebaseUser driver;
    private Car car;

    private TripBuilder() {
        path = new ArrayList<>();

    }

    public static TripBuilder startTrip(FirebaseUser driver, Car car) {
        TripBuilder tripBuilder = new TripBuilder();

        tripBuilder.driver = driver;
        tripBuilder.car = car;

        tripBuilder.whenStarted = new Date();

        return tripBuilder;
    }

    public TripBuilder addLocation(Location location) {
        path.add(location);

        return this;
    }

    public TripBuilder endTrip() {
        whenEnded = new Date();

        return this;
    }

    private ArrayList<LatLng> convertLocationsToLatLngs(ArrayList<Location> path) {
        ArrayList<LatLng> latLngs = new ArrayList<>(path.size());

        for (Location location : path) {
            latLngs.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }
        return null;
    }

    public Trip build() {
        return new Trip(
                whenStarted,
                whenEnded,
                driver,
                car,
                convertLocationsToLatLngs(path)
        );
    }

    public TripBuilder save() {
        FirebaseHandler firebaseHandler = new FirebaseHandler();

        return this;
    }
}
