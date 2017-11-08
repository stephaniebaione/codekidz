package com.example.regina.ratapp.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.regina.ratapp.R;

public class UserSettingsActivity extends AppCompatActivity {
    /**
     * On create method, create all instances of the objects in which the user inputs
     * data of the rat report and the button to help the user navigate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersettings);


        populateEmail();
    }

    /**
     * sets the email textview to have the current user's email
     */
    public void populateEmail() {
        String em = getIntent().getExtras().getString("Email");
        ((TextView) findViewById(R.id.EmailBlank)).setText(em);
    }
}
