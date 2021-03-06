package com.example.regina.ratapp.Model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Manager that sorts through the selected date range chosen by the user and calculates the reports
 * made in each month.
 */

public class GraphQueryManager {

    private final com.example.regina.ratapp.Controller.GraphActivity activity;

    /**
     * constructor for this class
     * @param activity what activity is passed in
     */
    public GraphQueryManager(com.example.regina.ratapp.Controller.GraphActivity activity) {
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
    public GraphQueryManager.DateSearcher getDateSearcherTask() {
        return new GraphQueryManager.DateSearcher();
    }

    /**
     * evaluates whether a date is valid and able to be graphed
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
            return firstMonth <= lastMonth;
        }
        return true;
    }
    public Boolean sameYear (int firstYearInt, int lastYearInt) {
        return firstYearInt == lastYearInt;

    }


    public class DateSearcher extends AsyncTask<Integer, Void, Integer> {
        final Dialog dialog = new ProgressDialog(activity);

        /**
         *
         * @param args takes in the first month,last month, first year, last month
         * @return a dummy int to move on
         */
        @Override
        public Integer doInBackground(Integer...args) {
            Query firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
            final SparseArray<RatReport> rightDateList = new SparseArray<>();
            final HashMap<String,Integer> dataPointList=new HashMap<>();
            final int firstMonthInt = args[0];
            final int firstYearInt = args[2];
            final int lastMonthInt = args[1];
            final int lastYearInt = args[3];


            firebaseDatabase.addListenerForSingleValueEvent (new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Debug","listened");
                    //int count = 2;
                    for (DataSnapshot ratData: dataSnapshot.getChildren()) {
                        String date = ratData.child("Created Date").getValue().toString();
                        String[] parts = date.split("/");
                        int month = Integer.parseInt(parts[0]);
                        int year = Integer.parseInt(parts[2].substring(0,4));
                        //Checks if dates chosen are through the same year
                        if ((year == firstYearInt) && (firstYearInt == lastYearInt)) {
                            // Checks if dates are between the two chosen months
                            if ((month == firstMonthInt) && (firstMonthInt == lastMonthInt)) {
                                if (dataPointList.containsKey(month+"")){
                                    int prevCount = dataPointList.get(month+"");
                                    prevCount++;
                                    dataPointList.remove(month+"");
                                    dataPointList.put(month+"",prevCount);
                                } else {
                                    dataPointList.put(month+"",1);
                                }

                                RatReport ratReport = createReport(ratData);
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                            } else if((month >= firstMonthInt)
                                    && (month <= lastMonthInt)) {
                                if (dataPointList.containsKey(month+"")){
                                    int prevCount = dataPointList.get(month+"");
                                    prevCount++;
                                    dataPointList.remove(month+"");
                                    dataPointList.put(month+"",prevCount);
                                } else {
                                    dataPointList.put(month+"",1);
                                }
                                RatReport ratReport = createReport(ratData);
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                            }
                            // Check statement for a span of more than one year
                        } else {
                            if (((year == firstYearInt) && (month >= firstMonthInt))
                                    || ((year > firstYearInt) && (year < lastYearInt))
                                    || ((year == lastYearInt) && (month <= lastMonthInt))) {
                                double testMonth = (month-1)/12.0;
                                if (dataPointList.containsKey(year+testMonth+"")){
                                    int prevCount = dataPointList.get(year+testMonth+"");
                                    prevCount++;
                                    dataPointList.remove(year+testMonth+"");
                                    dataPointList.put(year+testMonth+"",prevCount);
                                } else {
                                    dataPointList.put(year+testMonth+"",1);
                                }
                                RatReport ratReport = createReport(ratData);
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                            }
                        }
                    }
                    activity.createGraph(dataPointList,sameYear(firstYearInt, lastYearInt));
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
                return 0;
            }
            return 1;

        }
        // creates a dialog box to show the user it is processing
        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loading");
            dialog.setCancelable(false);
            dialog.show();

        }
        //closes dialog when done
        @Override
        protected void onPostExecute(Integer searched) {
            dialog.dismiss();
            Log.d("Debug", "executed");

        }


    }


}
