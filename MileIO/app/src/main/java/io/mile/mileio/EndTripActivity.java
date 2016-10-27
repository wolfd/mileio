package io.mile.mileio;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import static io.mile.mileio.TrackingService.TRACKING;

/**
 * Ends the trip, removes the ongoing notification, and stops the service
 * then saves the data
 */

public class EndTripActivity extends AppCompatActivity {
    public static final String LOCATION = "ArrayList<Location>";
    public static final String DISTANCE = "float";

    public static float distance;

    private ArrayList<Location> locations;
    private BroadcastReceiver receiver;
    private IntentFilter filter;

    // TODO testing?
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        // TODO testing?
        textView = (TextView) findViewById(R.id.distance_text);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(MainActivity.MAP_NOTIFICATION_ID);

        // set up arraylist to hold all the location updates and distance at 0
        locations = new ArrayList<>();
        distance = 0;

        // set up message receiver from service
        filter = new IntentFilter();
        filter.addAction(TRACKING);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                locations = intent.getParcelableArrayListExtra(LOCATION);
                distance = intent.getFloatExtra(DISTANCE, 0);
            }
        };

        registerReceiver(receiver, filter);

        stopService(new Intent(this, TrackingService.class));
    }

    private void saveTrip() {

    }

    // TODO flesh out this activity to have all end activity information/layout

    // unregister message receiver
    @Override
    protected void onDestroy() {
        textView.setText("Trip distance: " + distance + " miles");
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
}
