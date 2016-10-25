package io.mile.mileio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static io.mile.mileio.MainActivity.MAP_NOTIFICATION_ID;

/**
 * Tracking service for GPS updates, saved in arraylist
 *
 * Guidance found from:
 * http://stackoverflow.com/questions/28535703/best-way-to-get-user-gps-location-in-background-in-android
 */
public class TrackingService extends Service {
    private static final String TAG = "TESTGPS";
    final static String TRACKING = "TRACKING";

    static final String LOCATION_LIST = "ARG_LOCATION_LIST";

    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000; //1000
    private static final float LOCATION_DISTANCE = 0; // 10f

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private ArrayList<Location> tripLocations;

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.d(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            tripLocations.add(location);

            updateNotification();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        tripLocations = new ArrayList<>();

        Log.d(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        startNotification();
        return START_STICKY;
    }

    private void startNotification() {
        // pending intent for ending trip
        PendingIntent pendingIntentDone = PendingIntent.getActivity(this, 0,
                new Intent(this, EndTripActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // build notification
        mBuilder = new NotificationCompat.Builder(getBaseContext())
                .setSmallIcon(R.drawable.ic_we_going_now_24dp)
                .setContentTitle("Trip in progress")
                .setContentText("Not yet tracking")
                .setOngoing(true)
                .setContentIntent(pendingIntentDone);

        mNotificationManager.notify(MAP_NOTIFICATION_ID, mBuilder.build());
    }

    private void updateNotification() {
        if (mBuilder == null) {
            return;
        }

        if (tripLocations.size() == 0) {
            return;
        }

        Location location = tripLocations.get(tripLocations.size() - 1);

        mBuilder.setContentText(
                String.format(Locale.US, "Location: %.5f, %.5f", location.getLatitude(), location.getLongitude())
        );

        mNotificationManager.notify(MAP_NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");

        initializeLocationManager();
        initializeNotificationManager();


        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    private void initializeNotificationManager() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}