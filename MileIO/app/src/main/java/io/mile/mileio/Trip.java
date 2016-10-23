package io.mile.mileio;

import com.google.firebase.auth.FirebaseUser;

public class Trip {
    FirebaseUser driver;
    double distance;

    public Trip(FirebaseUser driver, double distance) {
        this.driver = driver;
        this.distance = distance;
    }
}
