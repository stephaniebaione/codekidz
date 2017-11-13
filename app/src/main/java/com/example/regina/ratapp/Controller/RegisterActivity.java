package com.example.regina.ratapp.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.regina.ratapp.Model.Admin;
import com.example.regina.ratapp.Model.User;
import com.example.regina.ratapp.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {
    private static FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    /* UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    */

    public RegisterActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //cancel button operation
        Button butt = (Button) findViewById(R.id.buttonC);
        butt.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {


            // Brings the user back to the welcome screen
            Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(i);
        }});
        Button secondRegisterButton = (Button) findViewById(R.id.buttonR);
        secondRegisterButton.setOnClickListener(new View.OnClickListener(){ @Override
        public void onClick(View v) {
            register();

        }});

    }
    //This gets all of the information the user put into the Register page
    private void register() {
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        final EditText email = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.passwordtext);
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        // Creates a Firebase User based on email and password passed in
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // if it cannot make new account then it will give a message saying why
                        if (!task.isSuccessful()) {
                            if (task.getException() != null) {
                                email.setError(task.getException().getMessage());
                            } else {
                                email.setError("There was an error");
                            }
                            email.requestFocus();
                        }

                    }
                });


        final RadioGroup userButton = (RadioGroup) findViewById(R.id.userType);
        Button registerButton = (Button) findViewById(R.id.buttonR);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userTypeId = userButton.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) userButton.findViewById(userTypeId);
                String userType = radioButton.getText().toString();
                if (makeNewAccount(email.getText().toString(),password.getText().toString(),
                        userType)) {
                    Intent r = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(r);
                }


            }
        });

    }
    // a helper that checks if account information exists already. If not it checks if it is a user
    // or admin and then adds information to account list

    public boolean makeNewAccount(String emailAddress, String givenPassword, String userType) {
        User newUser = new User(emailAddress, givenPassword);
        Admin newAdmin = new Admin(emailAddress, givenPassword);

        if (!(newUser.doesAccountExist(emailAddress))) {
            if (userType.equals("User")) {
                newUser.addNewUser(newUser);
                if (newUser.getAccounts().containsKey(emailAddress)) {
                    return true;
                }
            }
        }
        if (!(newAdmin.doesAccountExist(emailAddress))) {
            if (userType.equals("Admin")) {
                newAdmin.addNewAdmin(newAdmin);
                if (newAdmin.getAdmins().containsKey(emailAddress)) {
                    return true;
                }
            }
        }
        return false;
    }

}
