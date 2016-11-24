package io.mile.mileio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
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

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;
import static io.mile.mileio.TrackingService.TRACKING;


// Good work! Come to me if you want more specific feedback
public class MainActivity extends AppCompatActivity {
    public static final int MAP_NOTIFICATION_ID = 11;
    private static final String TAG = "MainActivity";

    // permission constants
    private final int GRANTED_FINE = 0;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String username;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get permission access, then start tracking
                checkPermissions();
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

    // verify we got location access, start tracking
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GRANTED_FINE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTracking();
                } else {
                    Toast.makeText(this, "Location needed for app to function", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkPermissions() {
        boolean hasFineLocation = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean hasCoarseLocation = ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (!hasFineLocation || !hasCoarseLocation) {
            return requestPermissions();
        } else {
            return true;
        }
    }

    private boolean requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, GRANTED_FINE);

        boolean hasCoarseLocation = ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean hasFineLocation = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        return hasCoarseLocation && hasFineLocation;
    }

    private void startTracking() {
        // start tracking service
        Log.d(TAG, "Starting TrackingService");
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
}
