package io.mile.mileio.types;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private final Date whenStarted;
    private final Date whenEnded;
    private final FirebaseUser driver;
    private final Car car;
    private final ArrayList<LatLng> path;

    public Trip(Date whenStarted, Date whenEnded, FirebaseUser driver, Car car, ArrayList<LatLng> path) {
        this.whenStarted = whenStarted;
        this.whenEnded = whenEnded;
        this.driver = driver;
        this.car = car;
        this.path = path;
    }

    public Date getWhenStarted() {
        return whenStarted;
    }

    public Date getWhenEnded() {
        return whenEnded;
    }

    public FirebaseUser getDriver() {
        return driver;
    }

    public Car getCar() {
        return car;
    }

    public ArrayList<LatLng> getPath() {
        return path;
    }
}
