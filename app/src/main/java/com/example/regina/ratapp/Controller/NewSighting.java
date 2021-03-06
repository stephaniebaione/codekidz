package com.example.regina.ratapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.EditText;

import com.example.regina.ratapp.Model.RatReport;
import com.example.regina.ratapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity that will create a new report and add it to the database based on what the user inputs.
 */
public class NewSighting extends AppCompatActivity {

    private EditText cityView;
    private EditText addressView;
    private EditText zipView;
    private EditText latView;
    private EditText longView;
    private Boolean uncompleted = false;
    private Spinner boroughSpinner;
    private Spinner locationSpinner;
    private DatePicker createdDate;
    private int prevKey;


    /**
     * On create method, create all instances of the objects in which the user inputs
     * data of the rat report and the button to help the user navigate
     * @param savedInstanceState state the app is in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreport);
        //instantiating the spinners
        instantiateSpinners();

        cityView = (EditText) findViewById(R.id.cityEdit);
        addressView = (EditText) findViewById(R.id.addressEdit);
        zipView = (EditText) findViewById(R.id.zipcodeEdit);
        latView= (EditText) findViewById(R.id.latitudeEdit);
        longView= (EditText) findViewById(R.id.longitudeEdit);
        createdDate = (DatePicker) findViewById(R.id.datePicker);

        FirebaseDatabase mBase = FirebaseDatabase.getInstance();
        Query prevReport = mBase.getReference().getRoot().limitToLast(1);
        prevReport.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot prev: dataSnapshot.getChildren()) {
                        if (prev.child("Unique Key").getValue(Double.class) != null) {
                            prevKey = prev.child("Unique Key").getValue(Double.class).intValue();
                        } else {
                            prevKey = 900000000;
                        }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //cancels making the report and goes back to main
        Button goBack = (Button) findViewById(R.id.cancelNR);
        goBack.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
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

    /**
     * creates the spinners and populates them with choices
     */
    private void instantiateSpinners() {
        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        boroughSpinner = (Spinner) findViewById(R.id.boroughSpinner);
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

    //check if any entry is empty or invalid then either alerts the user to a mistake or
    // makes the report
    private void attemptReport(){
        View focusView = null;
        if (checkIfEmpty(addressView)){
            uncompleted = true;
            focusView=addressView;
            addressView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(cityView)){
            uncompleted = true;
            focusView=cityView;
            cityView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(zipView)){
            uncompleted = true;
            focusView=zipView;
            zipView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(latView)){
            uncompleted = true;
            focusView=latView;
            latView.setError("Field cannot be empty.");
        } else if (checkIfEmpty(longView)) {
            uncompleted = true;
            focusView = longView;
            longView.setError("Field cannot be empty.");
        } else if (zipView.getText().toString().trim().length() < 5) {
            uncompleted = true;
            focusView=zipView;
            zipView.setError("Zipcode must be 5 numbers.");
        }
        if (uncompleted){

                assert focusView != null;
                focusView.requestFocus();

        } else {
            int day = createdDate.getDayOfMonth();
            int month = createdDate.getMonth() + 1;
            int year = createdDate.getYear();
            //base case
            if ((11464394 <= prevKey) && (prevKey <= 37018532)){
                prevKey=37100000;
            }
            prevKey+=7;
            RatReport newReport = new RatReport(prevKey,""+month+"/"+day+"/"+year,
                    locationSpinner.getSelectedItem().toString(),zipView.getText().toString(),
                    addressView.getText().toString(),
                    cityView.getText().toString(), boroughSpinner.getSelectedItem().toString(),
                    Double.parseDouble(latView.getText().toString()),
                    Double.parseDouble(longView.getText().toString()));
            pushRatDataToFirebase(newReport);  //UNCOMMENT THIS WHEN WE WANT THE DATA TO ACTUALLY BE
                                               // PUSHED TO FIREBASE
            //userUpdate();
            Intent back = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(back);
        }
    }

    /**
     * Checks whether the user provide information in the Report
     * @param etText an EditText box from the New Sighting Page
     * @return a boolean value representing whether the EditText was empty
     */
    private boolean checkIfEmpty(EditText etText) {
        return etText.getText().toString().trim().isEmpty();
    }


    /**
     *  Method that makes a HashMap from the RatReport and pushes the data to Firebase
     * @param rat takes in a Rat Report that needs to be added to Firebase
     */
    private void pushRatDataToFirebase(RatReport rat) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> newRatReport = new HashMap<>();
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
}


