package com.example.regina.ratapp.Model;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.regina.ratapp.Controller.*;
import com.example.regina.ratapp.Controller.MapsActivity;
import com.example.regina.ratapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Abby on 10/26/2017.
 */

public class QueryManager {
     HashMap<Integer, RatReport> rightDateList = new HashMap<Integer, RatReport>();
    public MapsActivity activity;


    public QueryManager(MapsActivity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
    /**
     * Sorts Data and saves the data that falls within the selected date range
     * @param firstMonth the earliest month chosen for viewing
     * @param lastMonth the latest month chosen for viewing
     * @param firstYear the first year the user wants to view
     * @param lastYear the same year or next year the user wants to view
     * @return
     */

    public HashMap<Integer, RatReport> getDateDataList(final String firstMonth, final String lastMonth,
                                                       final String firstYear, final String lastYear) {
        Log.d("Testing", "get here");
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Testing", "run");
                int firstMonthInt = Integer.parseInt(firstMonth);
                int lastMonthInt = Integer.parseInt(lastMonth);
                int firstYearInt = Integer.parseInt(firstYear);
                int lastYearInt = Integer.parseInt(lastYear);
                for (DataSnapshot ratData: dataSnapshot.getChildren()) {
                    String date = ratData.child("Created Date").getValue().toString();
                    Log.d("BAAAAAAA", "meh" + ratData.getChildrenCount());
                    String[] parts = date.split("/");
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[parts.length - 1].substring(0,4));
                    //Checks if dates chosen are through the same year
                    if (year == firstYearInt && firstYearInt == lastYearInt) {
                        // Checks if dates are between the two chosen months
                        if(month >= firstMonthInt
                                && month <= lastMonthInt) {
                            RatReport ratReport = createReport(ratData);
                           addtoRightDateList(ratReport);
                            Log.d("whhhhyyyyy", "newval" + " " + rightDateList.size());

                        }
                        // Check statement for a span of more than one year
                    } else {
                        if ((year == firstYearInt && month >= firstMonthInt) || (year > firstYearInt
                                && year < lastYearInt) || (year == lastYearInt
                                && month <= lastMonthInt)) {
                            RatReport ratReport = createReport(ratData);
                            addtoRightDateList(ratReport);
                            Log.d("whhhhyyyyy", "newval" + " " + rightDateList.size());
                        }
                    }
                }
                Log.d("whhhhyyyyy", "final" + " " + rightDateList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        Log.d("YOOOOOOOOO", "check" + " " + rightDateList.size());
        return getRightDateList();


    }
    public void addtoRightDateList(RatReport report) {
        rightDateList.put(report.getUniqueKey(), report);
    }

    public HashMap<Integer, RatReport> getRightDateList() {
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
    public DateSearcher getDateSearcherTask() {
        return new DateSearcher();
    }

    public class DateSearcher extends AsyncTask<String, Void, Integer> {
        Dialog dialog = new ProgressDialog(activity);

        @Override
        public Integer doInBackground(String...args) {
            Query firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
            final HashMap<Integer, RatReport> rightDateList = new HashMap<>();
            final String firstMonth = args[0];
            final String firstYear = args[2];
            final String lastMonth = args[1];
            final String lastYear = args[3];

            firebaseDatabase.addListenerForSingleValueEvent (new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Debug","listened");

                    int firstMonthInt = Integer.parseInt(firstMonth);
                    int lastMonthInt = Integer.parseInt(lastMonth);
                    int firstYearInt = Integer.parseInt(firstYear);
                    int lastYearInt = Integer.parseInt(lastYear);
                    //int count = 2;
                    for (DataSnapshot ratData: dataSnapshot.getChildren()) {
                        String date = ratData.child("Created Date").getValue().toString();
                        String[] parts = date.split("/");
                        int month = Integer.parseInt(parts[0]);
                        int year = Integer.parseInt(parts[2].substring(0,4));
                        //Checks if dates chosen are through the same year
                        if (year == firstYearInt && firstYearInt == lastYearInt) {
                            // Checks if dates are between the two chosen months
                            if(month >= firstMonthInt
                                    && month <= lastMonthInt) {
                                RatReport ratReport = createReport(ratData);
                                LatLng sydney = new LatLng(ratReport.getLatitude(), ratReport.getLongitude());
                                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                                activity.addMarkers(ratReport);
                                Log.d("hhhhhhhhhhhhhhh", "being added" + rightDateList.size());
                            }
                            // Check statement for a span of more than one year
                        } else {
                            if ((year == firstYearInt && month >= firstMonthInt) || (year > firstYearInt
                                    && year < lastYearInt) || (year == lastYearInt
                                    && month <= lastMonthInt)) {
                                RatReport ratReport = createReport(ratData);
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                                activity.addMarkers(ratReport);
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Debug", "Canceled");
                }
            });
            Log.d("hhh22222", "second" + rightDateList.size());
            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                int x = 7;
            }
            return 7;

        }

        protected void onPreExecute() {
            dialog.setTitle("Loading");
            dialog.setCancelable(false);
            dialog.show();
        }
        protected void onPostExecute(Integer searched) {
            //activity.addMarkers(searched);
            dialog.dismiss();
            Log.d("Debug", "executed");

        }


    }
    //*****************************************************************************************
    public class DateSearcher2 extends AsyncTask<Void, Void, Integer> {
        Dialog dialog = new ProgressDialog(activity);
        DateSearcher2(String firstMonth,String firstYear, String lastMonth, String lastYear) {
            this.firstMonth = firstMonth;
            this.firstYear = firstYear;
            this.lastMonth = lastMonth;
            this.lastYear = lastYear;
        }
        final String firstMonth;
        final String firstYear;
        final String lastMonth;
        final String lastYear;
        @Override
        public Integer doInBackground(Void...params) {

            return 7;

        }

        protected void onPreExecute() {
            dialog.setTitle("Loading");
            dialog.setCancelable(false);
            dialog.show();
            Query firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
            final HashMap<Integer, RatReport> rightDateList = new HashMap<>();

            firebaseDatabase.addListenerForSingleValueEvent (new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Debug","listened");

                    int firstMonthInt = Integer.parseInt(firstMonth);
                    int lastMonthInt = Integer.parseInt(lastMonth);
                    int firstYearInt = Integer.parseInt(firstYear);
                    int lastYearInt = Integer.parseInt(lastYear);
                    //int count = 2;
                    for (DataSnapshot ratData: dataSnapshot.getChildren()) {
                        String date = ratData.child("Created Date").getValue().toString();
                        String[] parts = date.split("/");
                        int month = Integer.parseInt(parts[0]);
                        int year = Integer.parseInt(parts[2].substring(0,4));
                        //Checks if dates chosen are through the same year
                        if (year == firstYearInt && firstYearInt == lastYearInt) {
                            // Checks if dates are between the two chosen months
                            if(month >= firstMonthInt
                                    && month <= lastMonthInt) {
                                RatReport ratReport = createReport(ratData);
                                LatLng sydney = new LatLng(ratReport.getLatitude(), ratReport.getLongitude());
                                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                                activity.addMarkers(ratReport);
                                Log.d("hhhhhhhhhhhhhhh", "being added" + rightDateList.size());
                            }
                            // Check statement for a span of more than one year
                        } else {
                            if ((year == firstYearInt && month >= firstMonthInt) || (year > firstYearInt
                                    && year < lastYearInt) || (year == lastYearInt
                                    && month <= lastMonthInt)) {
                                RatReport ratReport = createReport(ratData);
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                                activity.addMarkers(ratReport);
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Debug", "Canceled");
                }
            });
            Log.d("hhh22222", "second" + rightDateList.size());
        }
        protected void onPostExecute(Integer searched) {
            //activity.addMarkers(searched);
            dialog.dismiss();
            Log.d("Debug", "executed");

        }


    }


}




