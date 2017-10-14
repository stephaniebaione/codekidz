package com.example.regina.ratapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.regina.ratapp.R.id.parent;


public class NewSighting extends AppCompatActivity {

    private EditText locTypeView;
    private EditText addressView;
    private EditText zipView;
    private EditText latView;
    private EditText longView;
    private Boolean uncompleted = false;

    private static int uniquekey = 4000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreport);
        //instantiating the spinners
        instantiateSpinners();

        //cancels making the report and goes back to main
        Button goBack = (Button) findViewById(R.id.cancelNR);
        goBack.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }});

        //confirms if report is valid and makes it
        Button makeReport = (Button) findViewById(R.id.confirm);
        makeReport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                attemptReport();
            }
        });
    }

    public void instantiateSpinners() {
        Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        Spinner boroughSpinner = (Spinner) findViewById(R.id.boroughSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this,
                R.array.location_types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> boroughAdapter = ArrayAdapter.createFromResource(this,
                R.array.borough_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locationSpinner.setAdapter(locationAdapter);
        boroughSpinner.setAdapter(boroughAdapter);
    }

    //check if any entry is empty or invalid then either alerts the user to a mistake or makes the report
    private void attemptReport(){
        View focusView = null;
        if (checkIfEmpty(locTypeView)){
            uncompleted = true;
            locTypeView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(addressView)){
            uncompleted = true;
            addressView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(zipView)){
            uncompleted = true;
            zipView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(latView)){
            uncompleted = true;
            latView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(longView)){
            uncompleted = true;
            longView.setError("Field cannot be empty.");
        }
        if (uncompleted){
            focusView.requestFocus();
        } else {
            Intent back = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(back);
        }
    }

    private boolean checkIfEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
    public void pushRatDataToFirebase(RatReport rat) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Map newRatReport = new HashMap();
        newRatReport.put("Unique Key", rat.getUniqueKey());
        newRatReport.put("Borough", rat.getBorough());
        newRatReport.put("City", rat.getCity());
        newRatReport.put("Created Date", rat.getCreatedData());
        newRatReport.put("Incident Address", rat.getIncidentAddress());
        newRatReport.put("Incident Zip", rat.getIncidentZip());
        newRatReport.put("Latitude", rat.getLatitude());
        newRatReport.put("Location Type", rat.getLocationType());
        newRatReport.put("Longitude", rat.getLongitude());
        database.push().setValue(newRatReport);
    }

/*    private void onItemSelected(AdapterView<?> parent, View view,
                                int pos, long id) {
        if (parent.getItemAtPosition(pos) == 0) {
            //needs to tell the user to select an item
        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }*/
}


