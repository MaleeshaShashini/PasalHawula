package com.s23010667.pasalhawula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Set the layout for Profile Activity

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set User Icon as selected when in Profile Activity
        bottomNavigationView.setSelectedItemId(R.id.ic_user);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int itemId = item.getItemId();

                if (itemId == R.id.ic_home) {
                    intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_search) {
                    intent = new Intent(ProfileActivity.this, FindResourcesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_add) {
                    intent = new Intent(ProfileActivity.this, AddResourcesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_bell) {
                    intent = new Intent(ProfileActivity.this, NotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_user) {
                    // If current activity is Profile, do nothing (already selected)
                    return true;
                }
                return false;
            }
        });
    }
}
