package io.mile.mileio;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 */

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    // TODO test value for user state (joining or starting car)
    public static int test = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // fragment switching logic
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // TODO get whether user belongs to car from firebase
        // check whether user is a part of a car yet, if not get them to join/create one
        if (test == 0) {
            JoinCarFragment joinCarFragment = new JoinCarFragment();
            transaction.add(R.id.setting_fragment, joinCarFragment, "joinFragment");
        } else {
            SettingsFragment settingsFragment = new SettingsFragment();
            transaction.add(R.id.setting_fragment, settingsFragment, "settingsFragment");
        }

        transaction.commit();

    }
}
