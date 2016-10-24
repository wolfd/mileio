package io.mile.mileio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
    MessageReceiver mReceiver;

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

        // pending intent for ending trip
        PendingIntent pendingIntentDone = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), EndTripActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // build notification
        mBuilder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_we_going_now_24dp)
                .setContentTitle("Trip in progress")
                .setContentText("Distance: " + distance)
                .setOngoing(true)
                .setContentIntent(pendingIntentDone);
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MAP_NOTIFICATION_ID, mBuilder.build());

        // start tracking message receiver
//        mReceiver = new MessageReceiver();
//        IntentFilter serviceFilter = new IntentFilter();
//        serviceFilter.addAction(TrackingService.TRACKING);
//        getActivity().registerReceiver(mReceiver, serviceFilter);

        // start tracking service
        getActivity().startService(new Intent(getActivity(), TrackingService.class));
    }

    @Override
    public void onStop() {
        Log.d("FRAGMENT", "on stop?");
//        getActivity().unregisterReceiver(mReceiver);
//        mNotificationManager.cancel(MAP_NOTIFICATION_ID);
        super.onStop();
    }

    // TODO implement permission check and verify for newer android versions
    public void checkPermissions() {

    }
}
