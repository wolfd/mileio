package io.mile.mileio;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private FirebaseUser driver;
    private Date when;
    private ArrayList<LatLng> path;

    public Trip(FirebaseUser driver, double distance, Date when, ArrayList<LatLng> path) {
        this.driver = driver;
        this.when = when;
        this.path = path;
    }

    public FirebaseUser getDriver() {
        return driver;
    }

    public void setDriver(FirebaseUser driver) {
        this.driver = driver;
    }

    public double getDistance() {

        return distance;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public ArrayList<LatLng> getPath() {
        return path;
    }

    public void setPath(ArrayList<LatLng> path) {
        this.path = path;
    }
}
