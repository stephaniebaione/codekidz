package com.example.regina.ratapp.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.regina.ratapp.R;

/**
 * Created by Stephanie on 10/26/2017.
 */

public class UserSettingsActivity extends AppCompatActivity {
    /**
     * On create method, create all instances of the objects in whch the user inputs
     * data of the rat report and the button to help the user navigate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersettings);

    }
}
