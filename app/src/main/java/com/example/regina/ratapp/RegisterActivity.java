package com.example.regina.ratapp;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button butt = (Button) findViewById(R.id.buttonC);
        register();
        butt.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(i);
        }});
    }

    private void register() {
        EditText email = (EditText) findViewById(R.id.editText);
        EditText password = (EditText) findViewById(R.id.passwordtext);
        final RadioGroup userButton = (RadioGroup) findViewById(R.id.userType);
        Button registerButton = (Button) findViewById(R.id.buttonR);
        registerButton.setOnClickListener((v) -> {
            userButton.getCheckedRadioButtonId();
        });
    }
}
