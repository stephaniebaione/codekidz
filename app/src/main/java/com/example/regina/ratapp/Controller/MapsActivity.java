package com.example.regina.ratapp.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Loader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.regina.ratapp.Model.Month;
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

    private Spinner stM;
    private Spinner stY;
    private Spinner endY;
    private Spinner endM;

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
        Button filterButton = (Button) findViewById(R.id.button2);

        makeSpinners();
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
        Button filterButton = (Button) findViewById(R.id.button2);
        final QueryManager datesearch = new QueryManager(MapsActivity.this);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner startMonthSpinner = (Spinner) findViewById(R.id.spinner2);
                Month startMonth = (Month) startMonthSpinner.getSelectedItem();
                int startMonthValue = Integer.parseInt(startMonth.getMonthCode());
                Spinner startYearSpinner = (Spinner)findViewById(R.id.spinner4);
                int startYear = Integer.parseInt((String)startYearSpinner.getSelectedItem());
                Spinner lastMonthSpinner = (Spinner) findViewById(R.id.spinner5);
                Month lastMonth = (Month) lastMonthSpinner.getSelectedItem();
                int lastMonthValue = Integer.parseInt(lastMonth.getMonthCode());
                Spinner lastYearSpinner = (Spinner)findViewById(R.id.spinner3);
                int lastYear = Integer.parseInt((String)lastYearSpinner.getSelectedItem());
                if (datesearch.validDates(startMonthValue,lastMonthValue,startYear,
                        lastYear)) {
                    mMap.clear();
                    QueryManager.DateSearcher newsearch = datesearch.getDateSearcherTask();
                    newsearch.execute(startMonthValue, lastMonthValue,
                            startYear,lastYear);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.7128, -74.0060)));
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
                    dialog.setCancelable(true);
                    dialog.setTitle("Oh rats! Something is not right");
                    dialog.setMessage("The dates entered are not in the right order." +
                            " Please make sure your start date is before the end date");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(true);
                    AlertDialog alertDialog = dialog.show();
                }

            }
        }
        );



    }
    public void addMarkers(RatReport addList) {
            LatLng marker = new LatLng(addList.getLatitude(), addList.getLongitude());
            mMap.addMarker(new MarkerOptions().position(marker).title("Unique Key:" + " "
                    + addList.getUniqueKey() + " " + "Created Date:" + addList.getCreatedData()));

    }

    public void makeSpinners(){
        stM = (Spinner) findViewById(R.id.spinner2);
        stY = (Spinner) findViewById(R.id.spinner4);
        endM = (Spinner) findViewById(R.id.spinner5);
        endY = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> yearAdapt = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> monthAdapt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Month.values());

        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stY.setAdapter(yearAdapt);
        endY.setAdapter(yearAdapt);
        stM.setAdapter(monthAdapt);
        endM.setAdapter(monthAdapt);
    }


}
