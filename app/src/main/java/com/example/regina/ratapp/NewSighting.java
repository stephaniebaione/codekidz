package com.example.regina.ratapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.regina.ratapp.R.id.parent;


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

    private static int uniquekey = 4000000;
    private static int counter =1;

    /**
     * On create method, create all instances of the objects in whch the user inputs
     * data of the rat report and the button to help the user navigate
     * @param savedInstanceState
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

    /**
     * creates the spinners and populates them with choices
     */
    public void instantiateSpinners() {
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

    //check if any entry is empty or invalid then either alerts the user to a mistake or makes the report
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
        } else if (checkIfEmpty(longView)){
            uncompleted = true;
            focusView=longView;
            longView.setError("Field cannot be empty.");
        }
        /*
         else if (locationSpinner.getSelectedItemPosition() == 0) {
            longView.setError("Field cannot be empty.");

            //this crashes, but this is the idea of what we want to do if we are adding a "PLEASE SELECT ONE" item for the spinners
        }
         */
        if (uncompleted){
            focusView.requestFocus();
        } else {
            int day = createdDate.getDayOfMonth();
            int month = createdDate.getMonth() + 1;
            int year = createdDate.getYear();
            RatReport newReport = new RatReport(4000000+counter*7,""+month+"/"+day+"/"+year,
                    locationSpinner.getSelectedItem().toString(),zipView.getText().toString(),
                    addressView.getText().toString(),
                    cityView.getText().toString(), boroughSpinner.getSelectedItem().toString(),
                    Double.parseDouble(latView.getText().toString()),Double.parseDouble(longView.getText().toString()));
            counter++;
            pushRatDataToFirebase(newReport);
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
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    /**
     *  Method that makes a HashMap from the RatReport and pushes the data to Firebase
     * @param rat takes in a Rat Report that needs to be added to Firebase
     */
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
}


