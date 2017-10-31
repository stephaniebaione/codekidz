package com.example.regina.ratapp.Model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.regina.ratapp.Controller.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Regina on 10/31/2017.
 */

public class GraphQueryManager {
    HashMap<Integer, RatReport> rightDateList = new HashMap<Integer, RatReport>();
    public com.example.regina.ratapp.Controller.GraphActivity activity;


    public GraphQueryManager(com.example.regina.ratapp.Controller.GraphActivity activity) {
        this.activity = activity;
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
    public GraphQueryManager.DateSearcher getDateSearcherTask() {
        return new GraphQueryManager.DateSearcher();
    }
    public Boolean validDates(int firstMonth, int lastMonth, int firstYear, int lastYear) {
        if (firstYear > lastYear) {
            return false;
        } else if(firstYear == lastYear) {
            if (firstMonth > lastMonth) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    public class DateSearcher extends AsyncTask<Integer, Void, Integer> {
        Dialog dialog = new ProgressDialog(activity);

        /**
         *
         * @param args takes in the first month,last month, first year, last month
         * @return a dummy int to move on
         */
        @Override
        public Integer doInBackground(Integer...args) {
            Query firebaseDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
            final HashMap<Integer, RatReport> rightDateList = new HashMap<>();
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
                            Log.d("aaaaaaaacheck1", " " + year + " first " + firstYearInt + " second " + lastYearInt );
                            // Checks if dates are between the two chosen months
                            if (month == firstMonthInt && firstMonthInt == lastMonthInt) {
                                Log.d("aaaaaaaacheck2", " " + year + " first " + firstMonthInt + " second " + lastMonthInt );

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
                            } else if(month >= firstMonthInt
                                    && month <= lastMonthInt) {
                                Log.d("aaaaaaaacheck3", " " + year + " first " + firstMonthInt + " second " + lastMonthInt );

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
                            if ((year == firstYearInt && month >= firstMonthInt) || (year > firstYearInt
                                    && year < lastYearInt) || (year == lastYearInt
                                    && month <= lastMonthInt)) {
                                Log.d("aaaaaaaacheck4", " " + year + " " + month +  " first " + firstYearInt + " second " + lastYearInt );
                                Log.d("aaaaaaaacheck4", " " + year + " first " + firstMonthInt + " second " + lastMonthInt );

                                if (dataPointList.containsKey(year+month+"")){
                                    int prevCount = dataPointList.get(year+month+"");
                                    prevCount++;
                                    dataPointList.remove(year+month+"");
                                    dataPointList.put(year+month+"",prevCount);
                                } else {
                                    dataPointList.put(year+month+"",1);
                                }

                                RatReport ratReport = createReport(ratData);
                                rightDateList.put(ratReport.getUniqueKey(), ratReport);
                            }
                        }
                    }
                    activity.createGraph(dataPointList);

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
        // creates a dialog box to show the user it is processing
        protected void onPreExecute() {
            dialog.setTitle("Loading");
            dialog.setCancelable(false);
            dialog.show();

        }
        //closes dialog when done
        protected void onPostExecute(Integer searched) {
            dialog.dismiss();
            Log.d("Debug", "executed");

        }


    }


}
