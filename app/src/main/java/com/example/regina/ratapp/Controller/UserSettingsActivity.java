package com.example.regina.ratapp.Controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regina.ratapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettingsActivity extends AppCompatActivity {
    private Button changePasswordBtn;
    private Button changeEmailBtn;
    private EditText newEmailField;
    private FirebaseAuth mAuth;
    private String uEmail;
    /**
     * On create method, create all instances of the objects in which the user inputs
     * data of the rat report and the button to help the user navigate
     * @param savedInstanceState an instance of userSettingsActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersettings);
        populateEmail();

        changePasswordBtn = (Button) findViewById(R.id.ChangePasswordButton);
        changeEmailBtn = (Button) findViewById(R.id.ChangeEmailButton);

        mAuth = FirebaseAuth.getInstance();
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(UserSettingsActivity.this);
                dialog1.setCancelable(true);
                dialog1.setTitle("Reset Password");
                dialog1.setMessage("An email will be sent to your email address.");
                dialog1.setPositiveButton("Ok", null);
                dialog1.show();
                Log.d("resting", "button clicked");
                mAuth.sendPasswordResetEmail(uEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("resting", "thing complete");
                        if (task.isSuccessful()){
                            Log.d("resting", "email sent");
                            AlertDialog.Builder dialog = new AlertDialog.Builder(UserSettingsActivity.this);
                            dialog.setCancelable(true);
                            dialog.setTitle("Reset Password");
                            dialog.setMessage("An email has been sent. Check your email to change.");
                            dialog.setPositiveButton("Ok", null);
                            dialog.show();

                        } else {
                            Log.d("resting", "not sent");
                            AlertDialog.Builder dialog2 = new AlertDialog.Builder(UserSettingsActivity.this);
                            dialog2.setCancelable(true);
                            dialog2.setTitle("Reset Password");
                            dialog2.setMessage("Email failed to send. Try again.");
                            dialog2.setPositiveButton("Ok", null);
                            dialog2.show();
                        }
                    }
                });
            }
        });

        newEmailField = (EditText) findViewById(R.id.emailField);
        changeEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIfEmpty(newEmailField)){
                    String inputEmail = newEmailField.getText().toString();
                    mAuth.getCurrentUser().updateEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("resting", "email update");
                            if (task.isSuccessful()){
                                AlertDialog.Builder dialog3 = new AlertDialog.Builder(UserSettingsActivity.this);
                                dialog3.setCancelable(true);
                                dialog3.setTitle("Email Change");
                                dialog3.setMessage("Email has been changed. Check email if you did not want this.");
                                dialog3.setPositiveButton("Ok", null);
                                dialog3.show();
                            } else {
                                AlertDialog.Builder dialog4 = new AlertDialog.Builder(UserSettingsActivity.this);
                                dialog4.setCancelable(true);
                                dialog4.setTitle("Email Change");
                                dialog4.setMessage("Email change unsuccessful. Try again.");
                                dialog4.setPositiveButton("Ok", null);
                                dialog4.show();
                            }
                        }
                    });
                }
            }
        });
        
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
     * sets the email textView to have the current user's email
     */
    private void populateEmail() {
        uEmail = getIntent().getExtras().getString("Email");
        ((TextView) findViewById(R.id.EmailBlank)).setText(uEmail);
    }
}
