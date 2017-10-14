package com.example.regina.ratapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewSighting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreport);
    }

    public void zipCodeCheck() {
        EditText zipCodeText = (EditText) findViewById(R.id.editText43);
        String zipCodeValue = zipCodeText.getText().toString();

    }
}
