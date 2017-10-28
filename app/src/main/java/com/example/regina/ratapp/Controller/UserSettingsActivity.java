package com.example.regina.ratapp.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.regina.ratapp.R;

public class UserSettingsActivity extends AppCompatActivity {

    String userEmail = getIntent().getExtras().getString("Email").toString();

    /**
     * On create method, create all instances of the objects in whch the user inputs
     * data of the rat report and the button to help the user navigate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersettings);

        populateEmail();
    }

    public void populateEmail() {
//        TextView emailSpace = (TextView) findViewById(R.id.EmailBlank);
//        emailSpace.setText(userEmail, TextView.BufferType.EDITABLE);

        //we need to load the email from the user into the EmailBlank
    }
}
