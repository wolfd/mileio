package io.mile.mileio;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final int MAP_NOTIFICATION_ID = 11;
    public static float distance;

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void startTracking() {
        // TODO remove testing distance value
        distance = 5;

        // build notification
        mBuilder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_we_going_now_24dp)
                .setContentTitle("Trip in progress")
                .setContentText("Distance: " + distance)
                .setOngoing(true);
        // TODO setContentIntent can make notification cancellable on click
//                .setContentIntent();
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MAP_NOTIFICATION_ID, mBuilder.build());

        // start tracking
        getActivity().startService(new Intent(getActivity(), TrackingService.class));

        // TODO remember to cancel notification on click somehow
//        mNotificationManager.cancel(MAP_NOTIFICATION_ID);
    }

    // TODO implement permission check and verify for newer android versions
    public void checkPermissions() {

    }
}
