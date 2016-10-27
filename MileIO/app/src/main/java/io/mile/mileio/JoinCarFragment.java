package io.mile.mileio;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 */
public class JoinCarFragment extends Fragment {
    private Button startCar;
    private Button joinCar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        startCar = (Button) view.findViewById(R.id.start_car);
        joinCar = (Button) view.findViewById(R.id.join_car);

        // sort of static code to show how it might work
        // TODO include db logic to set user as car owner and generate new id
        startCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.test = 1;

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                SettingsFragment settingsFragment = new SettingsFragment();
                transaction.add(R.id.setting_fragment, settingsFragment, "settingsFragment");
                transaction.commit();
            }
        });

        joinCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinCarDialog();
            }
        });

        return view;
    }

    private void joinCarDialog() {
        final EditText input = new EditText(getContext());
        // set focus and popup keyboard
        input.setSelectAllOnFocus(true);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                .setTitle(R.string.join_title)
                .setView(input)
                .setCancelable(true)
                .setPositiveButton(R.string.join_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO logic here for db connection checking that a car exists
                        Toast.makeText(getContext(), "You joined a new car ...", Toast.LENGTH_SHORT);
                    }
                });

        AlertDialog visibleAlert = alert.create();
        // popup the soft keyboard when the alert dialog opens, close when alert dialog closes
        visibleAlert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        visibleAlert.show();
    }

    // sort of static code to show how it might work
    private void setStartCar() {
        SettingsActivity.test = 1;
        Log.d("TEST", "joined car");
        Toast.makeText(getContext(), "New car started", Toast.LENGTH_SHORT).show();
    }
}
