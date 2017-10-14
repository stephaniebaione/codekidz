package com.example.regina.ratapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class NewSighting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreport);

        //instantiating the spinners
        instantiateSpinners();


    }

    public void instantiateSpinners() {
        Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        Spinner boroughSpinner = (Spinner) findViewById(R.id.boroughSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this,
                R.array.location_types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> boroughAdapter = ArrayAdapter.createFromResource(this,
                R.array.location_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locationSpinner.setAdapter(locationAdapter);
        boroughSpinner.setAdapter(boroughAdapter);
    }
}
