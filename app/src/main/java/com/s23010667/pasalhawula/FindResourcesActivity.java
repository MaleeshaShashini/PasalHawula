package com.s23010667.pasalhawula; // Your package name

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FindResourcesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_resources); // Set the layout for FindResources Activity

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Search Icon as selected when in FindResources Activity
        bottomNavigationView.setSelectedItemId(R.id.ic_search);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int itemId = item.getItemId();

                if (itemId == R.id.ic_home) {
                    intent = new Intent(FindResourcesActivity.this, HomeActivity.class);
                    // These flags manage the activity back stack correctly.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish(); // Finish current activity
                    return true;
                } else if (itemId == R.id.ic_search) {
                    // If current activity is FindResourcesActivity, do not restart.
                    return true;
                } else if (itemId == R.id.ic_add) {
                    intent = new Intent(FindResourcesActivity.this, AddResourcesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_bell) {
                    intent = new Intent(FindResourcesActivity.this, NotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_user) {
                    intent = new Intent(FindResourcesActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
