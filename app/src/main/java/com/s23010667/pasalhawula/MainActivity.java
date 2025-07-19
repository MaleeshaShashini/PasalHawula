package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View; // Import View class for OnClickListener
import android.widget.TextView; // Import TextView class
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private TextView txtSignup; // Declare the TextView variable
    private TextView txtForgotPassword;
    private DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Login button by its ID
        Button btnLogin = findViewById(R.id.btnLogin);

        txtSignup = findViewById(R.id.txtSignup); // Initialize txtSignup
        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        myDb = new DatabaseHelper(this);

        // Set a click listener for the Login button
        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            } else if (myDb.checkUser(email, password)) {
                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
            }
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
