package com.example.regina.ratapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //SimpleCursorAdapter mAdapter;
    static RatReport dummy = new RatReport(0,"a","a",3,"a","a","",22,22);
    private ListView lol;

    ArrayList<String> PROJ = new ArrayList<String>(Arrays.asList("bla","ble", "blo"));
    private String[] lv_arr = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratDataManipulator();

        // Get a handle to the list view
        ListView lv = (ListView) findViewById(R.id.ratList);

        // Convert ArrayList to array
        //lv_arr = PROJ.toArray();
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PROJ));
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
    }
    public void ratDataManipulator() {
        DatabaseReference ratData = FirebaseDatabase.getInstance().getReference();
        //final Button getDataButton = (Button) findViewById(R.id.getData);

        // Attach a listener to read the data at our posts reference
        ratData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ratSnapshot: dataSnapshot.getChildren()) {
                    RatReport ratData = ratSnapshot.getValue(RatReport.class);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });





    }
}
