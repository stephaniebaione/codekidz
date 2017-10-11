package com.example.regina.ratapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //SimpleCursorAdapter mAdapter;
    static RatReport dummy = new RatReport(0,"a","a",3,"a","a","",22,22);
    public ListView listView;
    public ArrayAdapter adapt;
    public List<String> PROJ = new ArrayList<String>(); //dummy.getReportList(); //new ArrayList<String>(Arrays.asList("bla","ble", "blo"));
    //public String[] lv_arr = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.ratList);
        Log.d("Testing", "created things");

        Query query = FirebaseDatabase.getInstance().getReference().child("dirtyrat-72570").limitToLast(50);


        final DatabaseReference ratData = FirebaseDatabase.getInstance().getReference("dirtyrat-72570");
        //PROJ=new ArrayList<String>();
        DatabaseReference topRef = ratData.child("dirtyrat-72570");
        //ratData=ratData.child("dirtyrat-72570");
        //final Button getDataButton = (Button) findViewById(R.id.getData);
        Log.d("Testing", "did access database");
        // Attach a listener to read the data at our posts reference
        ratData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Testing", "data changed caleled");
                if (dataSnapshot.hasChildren()){
                    Log.d("Testing", "Has children");
                }
                //this is an ugly solution, will change
//                try{
//                    Thread.sleep(1000);
//                }catch(Exception ignored){};
                //
//                RatReport ratR2 = dataSnapshot.getValue(RatReport.class);
//                String address2 = ratR2.getIncidentAddress() + " " + ratR2.getCreatedData();
//                PROJ.add(address2);
                Log.d("Testing", "actul add");
                for (DataSnapshot ratSnapshot: dataSnapshot.getChildren()) {
                    RatReport ratR = new RatReport(
                            ratSnapshot.child("Unique Key").getValue(Integer.class),
                            ratSnapshot.child("Created Data").getValue().toString(),
                            ratSnapshot.child("Location Type").getValue().toString(),
                            ratSnapshot.child("Incident Zip").getValue(Integer.class),
                            ratSnapshot.child("Incident Address").getValue().toString(),
                            ratSnapshot.child("City").getValue().toString(),
                            ratSnapshot.child("Borough").getValue().toString(),
                            ratSnapshot.child("Latitude").getValue(Double.class),
                            ratSnapshot.child("Longitude").getValue(Double.class)
                    );
                    String address = ratR.getIncidentAddress() + " " + ratR.getCreatedData();
                    PROJ.add(address);
                    Log.d("Testing", "actul add");


//                    else if (PROJ.size() > 1){
//                        adapt.notifyDataSetChanged();
//                    }
//                    //formats the datasnapshot entries to strings

                    Log.d("Testing", "This actually worked");
//                    RatReport ratData = ratSnapshot.getValue(RatReport.class);
//                    PROJ.add(ratSnapshot.child("Incident Address").getValue(String.class));
//                    Log.d("TAG","please just work");

                }
                //listView.setAdapter(adapt);
                Log.d("Testing", "gained adapter");
                //adapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        Log.d("Testing", "went through that");
        //ratData.addValueEventListener(eventListener);
//        adapt=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, PROJ);
//        ListView listView= (ListView) findViewById(R.id.ratList);
//        listView.setAdapter(adapt);

        //topRef.addListenerForSingleValueEvent(eventListener);
        //ratDataManipulator();

        // Get a handle to the list view
//        ListView lv = (ListView) findViewById(R.id.ratList);
//
//        // Convert ArrayList to array
//        //lv_arr = (String[]) PROJ.toArray();
//        lv.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1,PROJ));
//        lol = (ListView) findViewById(R.id.ratList);
//        List<String> ratList = PROJ;
//        ArrayAdapter<String> arrayA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ratList);
//        lol.setAdapter(arrayA);

//        String[] fromColumns = {PROJ};
//        int[] toViews = {android.R.id.text1};
//        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,null,PROJ,toViews,0 );
//        setList

        Button butt = (Button) findViewById(R.id.logOut);
        butt.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(i);
        }});
        Log.d("Testing", "what the what");

        adapt = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,PROJ);
        listView.setAdapter(adapt);
        //listView.setTextFilterEnabled(true);
        Log.d("Testing", "Size is: " + PROJ.size());

        adapt.notifyDataSetChanged();
    }
    public void ratDataManipulator() {
        DatabaseReference ratData = FirebaseDatabase.getInstance().getReference();
        DatabaseReference topRef = ratData.child("dirtyrat-72570");
        //final Button getDataButton = (Button) findViewById(R.id.getData);

        // Attach a listener to read the data at our posts reference
       ValueEventListener eventListener = new ValueEventListener() {
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
        };
        topRef.addListenerForSingleValueEvent(eventListener);





    }
}
