package io.mile.mileio.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import io.mile.mileio.types.Trip;

/**
 * An interface for writing data to Firebase.
 */

public class FirebaseHandler {
    private DatabaseReference mDatabase;

    public FirebaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void saveTrip(Trip trip) {
        // save trip under trips/{uid}/{key}

        String key = mDatabase.child("trips").push().getKey();

        DatabaseReference newTripRef = mDatabase
                .child("trips")
                .child(trip.getDriver().getUid())
                .child(key);

        newTripRef.setValue(trip.toMap());
    }
}
