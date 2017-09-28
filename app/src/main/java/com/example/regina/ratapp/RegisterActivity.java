package com.example.regina.ratapp;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //cancel button operation
        Button butt = (Button) findViewById(R.id.buttonC);
        register();
        butt.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(i);
        }});
    }
    //This gets all of the information the user put into the Register page
    private void register() {
        final EditText email = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.passwordtext);
        final RadioGroup userButton = (RadioGroup) findViewById(R.id.userType);
        Button registerButton = (Button) findViewById(R.id.buttonR);
        registerButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int userTypeId = userButton.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) userButton.findViewById(userTypeId);
                        String userType = radioButton.getText().toString();
                        makeNewAccount(email.getText().toString(),password.getText().toString(),
                                userType);
                        Intent r = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(r);


                    }
        });

    }
    // a helper that checks if account information exists already. If not it checks if it is a user or admin and then adds
    // information to account list
    public void makeNewAccount(String emailAddress, String givenPassword, String userType) {
        User newUser = new User(emailAddress, givenPassword);
        //Log.d("UserType", userType);
        Boolean test =newUser.doesAccountExist(emailAddress);
        //Log.d("account exiting", test.toString());
        if (!(newUser.doesAccountExist(emailAddress))) {
            if (userType.equals("User")) {
                newUser.addNewUser(newUser);
                if (newUser.getAccounts().containsKey(emailAddress)) {
                    //Log.d("true","True");
                }
            }
        }
    }

}
