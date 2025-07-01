package com.s23010667.pasalhawula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.view.View;
import com.google.android.material.card.MaterialCardView; // import MaterialCardView


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Set the layout for Home Activity

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Icon as selected when in Home Activity
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
        MaterialCardView cardListResources = findViewById(R.id.cardListResources);

        if (cardListResources != null) {
            cardListResources.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, AddResourcesActivity.class);
                    startActivity(intent);
                }
            });
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int itemId = item.getItemId();

                if (itemId == R.id.ic_home) {
                    // If current activity is Home, do nothing (already selected)
                    return true;
                } else if (itemId == R.id.ic_search) {
                    intent = new Intent(HomeActivity.this, FindResourcesActivity.class);
                    // Manage activity stack to prevent multiple instances
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish(); // Finish current activity
                    return true;
                } else if (itemId == R.id.ic_add) {
                    intent = new Intent(HomeActivity.this, AddResourcesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_bell) {
                    intent = new Intent(HomeActivity.this, NotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_user) {
                    intent = new Intent(HomeActivity.this, ProfileActivity.class);
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
