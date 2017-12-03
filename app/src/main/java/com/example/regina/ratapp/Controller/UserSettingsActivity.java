package com.example.regina.ratapp.Controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regina.ratapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettingsActivity extends AppCompatActivity {
    private Button changePasswordBtn;
    private Button changeEmailBtn;
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
        changePasswordBtn = (Button) findViewById(R.id.ChangeEmailButton);

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
        
    }

    /**
     * sets the email textView to have the current user's email
     */
    private void populateEmail() {
        uEmail = getIntent().getExtras().getString("Email");
        ((TextView) findViewById(R.id.EmailBlank)).setText(uEmail);
    }
}
