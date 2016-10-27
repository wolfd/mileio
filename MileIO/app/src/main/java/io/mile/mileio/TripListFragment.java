package io.mile.mileio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import io.mile.mileio.types.Car;
import io.mile.mileio.types.Trip;
import io.mile.mileio.types.TripBuilder;

public class TripListFragment extends Fragment {
    private static final String TAG = "TripListFragment";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView tripRecyclerView;
    private RecyclerView.Adapter tripAdapter;

    private ArrayList<Trip> trips;
    private FirebaseUser user;

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

        user = FirebaseAuth.getInstance().getCurrentUser();


        trips.add(TripBuilder.startTrip(user, new Car("Fake car")).build());

        fetchTripList();
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

    private void fetchTripList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference tripsReference = database.getReference("trips");

        // TODO change to car reference so you can see all the trips for the car (?)
        final DatabaseReference myTripsReference = tripsReference.child(user.getUid());

        myTripsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Trip> trips = new ArrayList<>();

                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                    trips.add(tripSnapshot.getValue(Trip.class));
                }

                setTrips(trips);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, String.valueOf(databaseError.toException()));
            }
        });
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
        tripAdapter.notifyDataSetChanged();
    }
}
