package com.example.regina.ratapp.Model;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.regina.ratapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Sorts Data and saves the data that falls within the selected date range
     * @param firstMonth the earliest month chosen for viewing
     * @param lastMonth the latest month chosen for viewing
     * @param firstYear the first year the user wants to view
     * @param lastYear the same year or next year the user wants to view
     * @return
     */
    public HashMap<Integer, RatReport> MapDataDateSearch(final String firstMonth, final String lastMonth, final String firstYear, final String lastYear) {
        Query firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        final HashMap<Integer, RatReport> rightDateList = new HashMap<>();
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int firstMonthInt = Integer.parseInt(firstMonth);
                int lastMonthInt = Integer.parseInt(lastMonth);
                int firstYearInt = Integer.parseInt(firstYear);
                int lastYearInt = Integer.parseInt(lastYear);
                for (DataSnapshot ratData: dataSnapshot.getChildren()) {
                    String date = ratData.child("Created Date").getValue().toString();
                    String[] parts = date.split("/");
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[2]);
                    //Checks if dates chosen are through the same year
                    if (year == firstYearInt && firstYearInt == lastYearInt) {
                        // Checks if dates are between the two chosen months
                        if(month >= firstMonthInt
                                && month <= lastMonthInt) {
                            RatReport ratReport = createReport(ratData);
                            rightDateList.put(ratReport.getUniqueKey(), ratReport);
                        }
                        // Check statement for a span of more than one year
                    } else {
                        if ((year == firstYearInt && month >= firstMonthInt) || (year > firstYearInt
                                && year < lastYearInt) || (year == lastYearInt
                                && month <= lastMonthInt)) {
                            RatReport ratReport = createReport(ratData);
                            rightDateList.put(ratReport.getUniqueKey(), ratReport);

                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rightDateList;
    }

    /**
     * Helper Function that makes a rat Report from given firebase data
     * @param ratSnapshot Data from Snapshot
     * @return a RatReport based on the firebase data
     */
    public RatReport createReport(DataSnapshot ratSnapshot) {
        Double latitude;
        try {
            latitude = ratSnapshot.child("Latitude").getValue(Double.class);
            latitude+=5;
            latitude-=5;
        } catch (Exception e) {
            latitude = 0.0;
        }
        Double longitude;
        try {
            longitude = ratSnapshot.child("Longitude").getValue(Double.class);
            longitude+=5;
            longitude-=5;
        } catch (Exception e) {
            longitude = 0.0;
        }
        //creates a new rat report and then adds it to a hashmap for later reference
        //and adds the key to an arraylist so it can be viewed in app
        RatReport ratR = new RatReport(
                ratSnapshot.child("Unique Key").getValue(Integer.class),
                ratSnapshot.child("Created Date").getValue().toString(),
                ratSnapshot.child("Location Type").getValue().toString(),
                ratSnapshot.child("Incident Zip").getValue().toString(),
                ratSnapshot.child("Incident Address").getValue().toString(),
                ratSnapshot.child("City").getValue().toString(),
                ratSnapshot.child("Borough").getValue().toString(),latitude,longitude
        );
        return ratR;
    }
}
