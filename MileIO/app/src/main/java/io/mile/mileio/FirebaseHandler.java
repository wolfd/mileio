package io.mile.mileio;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An interface for writing data to Firebase.
 */

public class FirebaseHandler {
    private DatabaseReference mDatabase;

    public FirebaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void saveTrip(Trip trip) {
        // save trip under trips/{uid}/{startTime}
        DatabaseReference newTripRef = mDatabase
                .child("trips")
                .child(trip.getDriver().getUid())
                .child(String.valueOf(trip.getWhen().getTime()));

        newTripRef.setValue(trip);
    }
}
