package io.mile.mileio;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class Trip {
    private final FirebaseUser driver;
    private final double distance;
    private final Date when;

    public Trip(FirebaseUser driver, double distance, Date when) {
        this.driver = driver;
        this.distance = distance;
        this.when = when;
    }

    public FirebaseUser getDriver() {
        return driver;
    }

    public double getDistance() {
        return distance;
    }

    public Date getWhen() {
        return when;
    }
}
