package io.mile.mileio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;
import static io.mile.mileio.TrackingService.TRACKING;

public class MainActivity extends AppCompatActivity {
    public static final int MAP_NOTIFICATION_ID = 11;
    public static float distance;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String username;
    private String email;

    private BroadcastReceiver receiver;
    private IntentFilter filter;

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up message receiver from service
        filter = new IntentFilter();
        filter.addAction(TRACKING);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "MESSAGE HERE ", Toast.LENGTH_SHORT).show();
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement permission check/get
                checkPermissions();
                startTracking();
            }
        });

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    RC_SIGN_IN);
        } else {
            username = firebaseUser.getDisplayName();

            if (firebaseUser.getEmail() != null) {
                email = firebaseUser.getEmail();
            }
        }

        MapsInitializer.initialize(this);
    }

    // TODO implement permissions check
    private void checkPermissions() {
    }

    private void startTracking() {
        // TODO remove testing distance value
        distance = 5;

        // pending intent for ending trip
        PendingIntent pendingIntentDone = PendingIntent.getActivity(this, 0,
                new Intent(this, EndTripActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // build notification
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_we_going_now_24dp)
                .setContentTitle("Trip in progress")
                .setContentText("Distance: " + distance)
                .setOngoing(true)
                .setContentIntent(pendingIntentDone);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MAP_NOTIFICATION_ID, mBuilder.build());

        // start tracking message receiver
        registerReceiver(receiver, filter);

        // start tracking service
        startService(new Intent(this, TrackingService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_sign_out) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }
                    });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // unregister message receiver
    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
}
