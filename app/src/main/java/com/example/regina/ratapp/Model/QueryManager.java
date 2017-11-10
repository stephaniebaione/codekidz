package com.example.regina.ratapp.Model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.example.regina.ratapp.Controller.MapsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


/**
 * Created by Abby on 10/26/2017.
 * Class is used for searching through the data
 */

public class QueryManager {
    //HashMap<Integer, RatReport> rightDateList = new HashMap<Integer, RatReport>();
    private final MapsActivity activity;
    private final ArrayList<RatReport> rightDateList = new ArrayList<>();


    public QueryManager(MapsActivity activity) {
        this.activity = activity;
    }


    /**
     * Helper Function that makes a rat Report from given Firebase data
     * @param ratSnapshot Data from Snapshot
     * @return a RatReport based on the Firebase data
     */
    private RatReport createReport(DataSnapshot ratSnapshot) {
        Double latitude;
        Double longitude;
        int uniqueKey;
        String createdDate;
        String locationType;
        String incidentZip;
        String incidentAddress;
        String city;
        String borough;

        try {
            latitude = ratSnapshot.child("Latitude").getValue(Double.class);
            latitude+=5;
            latitude-=5;
            longitude = ratSnapshot.child("Longitude").getValue(Double.class);
            longitude+=5;
            longitude-=5;
        } catch (Exception e) {
            latitude = 0.0;
            longitude = 0.0;
        }
        if (ratSnapshot.child("Unique Key") != null) {
            uniqueKey = ratSnapshot.child("Unique Key").getValue(Integer.class);
        } else{
            uniqueKey = 0;
        }
        if (ratSnapshot.child("Created Date").getValue() != null) {
            createdDate = ratSnapshot.child("Created Date").getValue().toString();
        } else {
            createdDate = "00/00/0000";
        }
        if (ratSnapshot.child("Location Type").getValue() != null) {
            locationType = ratSnapshot.child("Location Type").getValue().toString();
        } else {
            locationType = "None";
        }
        if (ratSnapshot.child("Incident Zip").getValue() != null) {
            incidentZip = ratSnapshot.child("Incident Zip").getValue().toString();
        } else {
            incidentZip = "other";
        }
        if (ratSnapshot.child("Incident Address").getValue() != null) {
            incidentAddress = ratSnapshot.child("Incident Address").getValue().toString();
        } else {
            incidentAddress = "Unknown";
        }
        if (ratSnapshot.child("City").getValue() != null) {
            city = ratSnapshot.child("City").getValue().toString();
        } else {
            city = "Unknown";
        }
        if (ratSnapshot.child("Borough").getValue() != null) {
            borough = ratSnapshot.child("Borough").getValue().toString();
        } else {
            borough = "unknown";
        }
        //creates a new rat report and then adds it to a Hash Map for later reference
        //and adds the key to an array list so it can be viewed in app
        return new RatReport(uniqueKey, createdDate, locationType,incidentZip, incidentAddress,city,
                borough, latitude,longitude

        );
    }
    /**
     * initializes the date searching class so we can use it
     * @return the date searcher instance
     */
    public DateSearcher getDateSearcherTask() {
        return new DateSearcher();
    }
    /**
     * evaluates whether a date is valid and able to be mapped
     * @param firstMonth start month
     * @param lastMonth end month
     * @param firstYear start year
     * @param lastYear end year
     * @return whether this date range is valid or not
     */
    public Boolean validDates(int firstMonth, int lastMonth, int firstYear, int lastYear) {
        if (firstYear > lastYear) {
            return false;
        } else if(firstYear == lastYear) {
            if (firstMonth > lastMonth) {
                return false;
            }
        }
        return true;
    }


    public class DateSearcher extends AsyncTask<Integer, Void, ArrayList<RatReport>> {
        final Dialog dialog = new ProgressDialog(activity);

        /**
         *
         * @param args takes in the first month,last month, first year, last month
         * @return a dummy int to move on
         */
        @Override
        public ArrayList<RatReport> doInBackground(Integer...args) {
            Query firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
            //HashMap<Integer, RatReport> rightDateList = new HashMap<>();
            final int firstMonthInt = args[0];
            final int firstYearInt = args[2];
            final int lastMonthInt = args[1];
            final int lastYearInt = args[3];
            firebaseDatabase.addListenerForSingleValueEvent (new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //int count = 2;
                    String date;

                    for (DataSnapshot ratData: dataSnapshot.getChildren()) {
                        if (ratData.child("Created Date").getValue() != null) {
                             date = ratData.child("Created Date").getValue().toString();
                        } else {
                            date = "00/00/0000";
                        }
                        String[] parts = date.split("/");
                        int month = Integer.parseInt(parts[0]);
                        int year = Integer.parseInt(parts[2].substring(0,4));
                        //Checks if dates chosen are through the same year
                        if ((year == firstYearInt) && (firstYearInt == lastYearInt)) {
                            // Checks if dates are between the two chosen months
                            if ((month == firstMonthInt && firstMonthInt == lastMonthInt)
                                    || (month >= firstMonthInt
                                    && month <= lastMonthInt)) {

                                RatReport ratReport = createReport(ratData);
                                rightDateList.add(ratReport);
                            }
                            // Check statement for a span of more than one year
                        } else {
                            if ((year == firstYearInt && month >= firstMonthInt)
                                    || (year > firstYearInt && year < lastYearInt)
                                    || (year == lastYearInt && month <= lastMonthInt)) {
                                RatReport ratReport = createReport(ratData);
                                rightDateList.add(ratReport);
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Debug", "Canceled");
                }
            });
            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                return rightDateList;
            }
            return rightDateList;


        }
        // creates a dialog box to show the user it is processing
        protected void onPreExecute() {
            dialog.setTitle("Loading");
            dialog.setCancelable(false);
            dialog.show();

        }
        //closes dialog when done
        protected void onPostExecute(ArrayList<RatReport> searched) {
            for (RatReport value: searched) {
                activity.addMarkers(value);
            }
            dialog.dismiss();

        }


    }


}




