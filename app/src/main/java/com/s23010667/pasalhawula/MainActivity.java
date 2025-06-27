package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View; // Import View class for OnClickListener
import android.widget.TextView; // Import TextView class

public class MainActivity extends AppCompatActivity {

    private TextView txtSignup; // Declare the TextView variable
    private TextView txtForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Login button by its ID
        Button btnLogin = findViewById(R.id.btnLogin);

        txtSignup = findViewById(R.id.txtSignup); // Initialize txtSignup
        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        // Set a click listener for the Login button
        btnLogin.setOnClickListener(v -> {
            // Create an Intent to open HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            // Start HomeActivity
            startActivity(intent);
            // Close the MainActivity so the user can't return to it
            finish();
        });

        // Set a click listener for the "Sign Up" TextView
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                // Start RegisterActivity
                startActivity(intent);


            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start ResetPasswordActivity
                Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
    }
}
