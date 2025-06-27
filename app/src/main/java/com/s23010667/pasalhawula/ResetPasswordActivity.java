package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View; // Import View class for OnClickListener
import android.widget.ImageView; // Import ImageView class
import android.os.Bundle;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start OTP Activity
                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
    }
}