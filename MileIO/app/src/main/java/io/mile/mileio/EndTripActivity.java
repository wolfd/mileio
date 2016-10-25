package io.mile.mileio;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Ends the trip, removes the ongoing notification, and stops the service
 * then saves the data
 */

public class EndTripActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(MainActivity.MAP_NOTIFICATION_ID);

        stopService(new Intent(this, TrackingService.class));

    }

    private void saveTrip() {

    }

    // TODO flesh out this activity to have all end activity information/layout
}
