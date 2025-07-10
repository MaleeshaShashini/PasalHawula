package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddRequestActivity extends AppCompatActivity {

    private ImageView imgBack; // The back arrow icon
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Home Activity
                // When the back button is clicked, navigate to the HomeActivity
                Intent intent = new Intent(AddRequestActivity.this, HomeActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
    }
}