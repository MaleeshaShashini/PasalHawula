package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View; // Import View class for OnClickListener
import android.widget.Button;
import android.widget.ImageView; // Import ImageView class
import android.os.Bundle;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button btnSendOTP = findViewById(R.id.btnSendOTP);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Login Activity
                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
        btnSendOTP.setOnClickListener(v -> {
            // Create an Intent to open  OTP Activity
            Intent intent = new Intent(ResetPasswordActivity.this, OTPActivity.class);
            // Start HomeActivity
            startActivity(intent);
            // Close the Reset Password Activity so the user can't return to it
            finish();
        });
    }
}