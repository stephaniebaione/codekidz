package com.example.regina.ratapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratDataManipulator();
        buttonLogic();
    }
    public void ratDataManipulator() {
        DatabaseReference ratData = FirebaseDatabase.getInstance().getReference();
//        final Button getDataButton = (Button) findViewById(R.id.getData);

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

    public void buttonLogic() {
        Button buttLogout = (Button) findViewById(R.id.logOut);
        Button buttNewSighting = (Button) findViewById(R.id.newSighting);
        buttLogout.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(i);
        }});
        buttNewSighting.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),NewSighting.class);
            startActivity(i);
        }});
    }
}
