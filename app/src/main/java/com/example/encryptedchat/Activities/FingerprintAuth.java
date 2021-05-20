package com.example.encryptedchat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.encryptedchat.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FingerprintAuth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_auth);

        getSupportActionBar().setTitle("Fingerprint Authentication");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Executor executor = Executors.newSingleThreadExecutor();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                    .setTitle("BeMe Fingerprint Authentication")
                    .setNegativeButton("Cancle", executor, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).build();

            Button authenticate = findViewById(R.id.authenticate);

            FingerprintAuth auth = this;

            authenticate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            auth.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FingerprintAuth.this, "Authenticated", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(FingerprintAuth.this,PhoneNumberVerification.class));
                                }
                            });
                        }
                    });
                }
            });



        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}