package io.mile.mileio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class TripListFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView tripRecyclerView;
    private RecyclerView.Adapter tripAdapter;

    private ArrayList<Trip> trips;

    public TripListFragment() {
        trips = new ArrayList<>();
    }

    public static TripListFragment newInstance() {
        TripListFragment fragment = new TripListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // if needed later
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        trips.add(new Trip(user, 10, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 5.4, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 25.8, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 25.8, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 25.8, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 25.8, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 25.8, new Date(), new ArrayList<LatLng>()));
        trips.add(new Trip(user, 25.8, new Date(), new ArrayList<LatLng>()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        tripRecyclerView = (RecyclerView) view.findViewById(R.id.trip_recycler_view);
        tripRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        tripRecyclerView.setLayoutManager(layoutManager);

        tripAdapter = new TripAdapter(trips);

        tripRecyclerView.setAdapter(tripAdapter);

        return view;
    }
}
