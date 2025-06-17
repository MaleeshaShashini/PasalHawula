package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Login button by its ID
        Button btnLogin = findViewById(R.id.btnLogin);

        // Set a click listener for the Login button
        btnLogin.setOnClickListener(v -> {
            // Create an Intent to open HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            // Start HomeActivity
            startActivity(intent);
            // Close the MainActivity so the user can't return to it
            finish();
        });
    }
}
