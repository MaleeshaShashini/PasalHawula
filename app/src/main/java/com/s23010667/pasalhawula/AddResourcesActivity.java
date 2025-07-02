package com.s23010667.pasalhawula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;// Used to link TabLayout with ViewPager2
import androidx.viewpager2.widget.ViewPager2; // Used for swipe able views (tabs)
public class AddResourcesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;// The navigation bar at the bottom
    private ImageView imgBack; // The back arrow icon
    private TabLayout tabLayout; // The segmented control (tabs for Physical, Digital, Human)
    private ViewPager2 viewPager; // The area where different forms (fragments) will be displayed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resources); // Set the layout for AddResources Activity

        bottomNavigationView = findViewById(R.id.bottom_navigation); //Initialize
        bottomNavigationView.setSelectedItemId(R.id.ic_add); // Set the 'Add' icon as selected in the bottom navigation bar
        imgBack = findViewById(R.id.imgBack);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this); // Create an adapter to manage the different fragments (forms)
        viewPager.setAdapter(adapter); // Link the adapter to the ViewPager2

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Home Activity
                // When the back button is clicked, navigate to the HomeActivity
                Intent intent = new Intent(AddResourcesActivity.this, HomeActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: // First tab (Physical)
                            tab.setText(R.string.physical_tab);
                            break;
                        case 1: // Second tab (Digital)
                            tab.setText(R.string.digital_tab);
                            break;
                        case 2:  // Third tab (Human)
                            tab.setText(R.string.human_tab);
                            break;
                    }
                }).attach(); // Attach the mediator to start linking them

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int itemId = item.getItemId();

                if (itemId == R.id.ic_home) {
                    intent = new Intent(AddResourcesActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_search) {
                    intent = new Intent(AddResourcesActivity.this, FindResourcesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_add) {
                    // If current activity is AddResources, do nothing (already selected)
                    return true;
                } else if (itemId == R.id.ic_bell) {
                    intent = new Intent(AddResourcesActivity.this, NotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.ic_user) {
                    intent = new Intent(AddResourcesActivity.this, ProfileActivity.class);
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
