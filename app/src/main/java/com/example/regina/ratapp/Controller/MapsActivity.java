package com.example.regina.ratapp.Controller;

import android.content.Loader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.regina.ratapp.Model.QueryManager;
import com.example.regina.ratapp.Model.RatReport;
import com.example.regina.ratapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    HashMap<Integer, RatReport> reportList = new HashMap<Integer, RatReport>();
    //private QueryManager thing = new QueryManager();

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
    public int month(String s) {
        String[] parts = s.split("/");
        String month = parts[0];
        int result = Integer.parseInt(month);
        return result;
    }
    public int year(String s) {
        String[] parts = s.split("/");
        String last = parts[parts.length - 1];
        String year = last.substring(0, 4);
        int result = Integer.parseInt(year);
        return result;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //thing.getDateDataList("01", "02", "2017", "2017").putAll(reportList);
        //Log.d("Boi", "size: " + reportList.size());
        QueryManager datesearch = new QueryManager(MapsActivity.this);
        QueryManager.DateSearcher newsearch = datesearch.getDateSearcherTask();
        newsearch.execute("01", "02", "2015","2015");
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void addMarkers(RatReport addList) {
       // for (RatReport value: addList.values()) {
            LatLng marker = new LatLng(addList.getLatitude(), addList.getLongitude());
            mMap.addMarker(new MarkerOptions().position(marker).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        //}

    }


}
