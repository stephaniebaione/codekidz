package com.example.regina.ratapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    public ArrayAdapter adapt;
    public HashMap<Integer, RatReport> reportHashMap = new HashMap<>();
    public List<String> PROJ = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creates our listview
        listView= (ListView) findViewById(R.id.ratList);
        Log.d("Testing", "created things");
        //our database references
        FirebaseDatabase database = getDatabase();
        Query query = database.getReference().getRoot().limitToLast(50);
        final DatabaseReference ratData = database.getReference("dirtyrat-72570");
        DatabaseReference topRef = ratData.child("dirtyrat-72570");
        Log.d("Testing", "did access database");
        // Attach a listener to read the data at our posts reference
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //troubleshooting
                Log.d("Testing", "data changed caleled");
                if (dataSnapshot.hasChildren()){
                    Log.d("Testing", "Has children");
                }
                //enables longitude and latitude to be read from our database
                for (DataSnapshot ratSnapshot: dataSnapshot.getChildren()) {
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
                    reportHashMap.put(ratR.getUniqueKey(), ratR);
                    String address = ""+ ratR.getUniqueKey();
                    PROJ.add(address);
                    Log.d("Testing", "actul add2");
                }
                Log.d("Testing", "gained adapter");
                adapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        Log.d("Testing", "went through that");
        buttonClicks();
        Log.d("Testing", "what the what");
        //adapter for list view and puts data in view
        adapt = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,PROJ);
        listView.setAdapter(adapt);
        Log.d("Testing", "Size is: " + PROJ.size());

        adapt.notifyDataSetChanged();
        //pulls up details pop up on item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reportId = (String) parent.getItemAtPosition(position);
                RatReport listR = reportHashMap.get(Integer.parseInt(reportId));

                AlertDialog.Builder adb = new AlertDialog.Builder(
                        MainActivity.this);
                adb.setTitle("Details");
                adb.setMessage(listR.createDataString(listR) );
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        });
    }
    public void ratDataManipulator() {
        DatabaseReference ratData = FirebaseDatabase.getInstance().getReference();
        DatabaseReference topRef = ratData.child("dirtyrat-72570");
        //final Button getDataButton = (Button) findViewById(R.id.getData);

        // Attach a listener to read the data at our posts reference
       //ValueEventListener eventListener = new ValueEventListener() {
        topRef.addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ratSnapshot: dataSnapshot.getChildren()) {
                    RatReport ratData = ratSnapshot.getValue(RatReport.class);
                    PROJ.add(ratSnapshot.child("Incident Address").getValue(String.class));
                    Log.d("TAG","worked");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //topRef.addListenerForSingleValueEvent(eventListener);
    }
    //gets the instance of our firebase database
    public FirebaseDatabase getDatabase() {
        FirebaseDatabase mBase = FirebaseDatabase.getInstance();
        //mBase.setPersistenceEnabled(true);
        return mBase;

    }

    public void buttonClicks() {
        //button that logs the user out
        Button logoutBtn = (Button) findViewById(R.id.logOut);
        logoutBtn.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(i);
        }});
        //button that takes user to sightingd
        Button newReportBtn = (Button) findViewById(R.id.logOut);
        newReportBtn.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),NewSighting.class);
            startActivity(i);
        }});
    }
}
