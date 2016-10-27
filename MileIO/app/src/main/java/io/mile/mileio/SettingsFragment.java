package io.mile.mileio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 */
public class SettingsFragment extends Fragment {
    private static final String CAR = "'s car";
    private TextView carOwner;
    private TextView carCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        carOwner = (TextView) view.findViewById(R.id.car_owner_name);
        carCode = (TextView) view.findViewById(R.id.car_add_code);

        carOwner.setText("DANNY DB STUFF" + CAR);
        carCode.setText(generateCode());

        return view;
    }

    // right now this returns a default value, because we don't have time to flesh out the logic
    private String generateCode() {
        return "ABC123abc123";
    }
}
