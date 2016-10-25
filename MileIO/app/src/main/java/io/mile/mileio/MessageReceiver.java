package io.mile.mileio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static io.mile.mileio.TrackingService.LOCATION_LIST;

/**
 * Message receiver for data passed from service to fragment, using code from:
 * http://android-coding.blogspot.in/2011/11/pass-data-from-service-to-activity.html
 */
public class MessageReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getParcelableArrayListExtra(LOCATION_LIST);
    }
}
