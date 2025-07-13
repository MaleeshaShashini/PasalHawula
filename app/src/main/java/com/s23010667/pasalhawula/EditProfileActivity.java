package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import android.widget.EditText; // for EditText
import android.widget.ImageView;
import android.widget.Toast; //  for Toast messages
import com.google.android.material.button.MaterialButton; //for MaterialButton
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;


public class EditProfileActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editSchoolName;
    private EditText editLocation;
    private EditText editPhoneNumber;
    private EditText editEmailAddress;
    private MaterialButton btnSaveChanges;
    private ActivityResultLauncher<Intent> mapSelectionLauncher;
    private double selectedLat = 0.0;
    private double selectedLng = 0.0;
    private String selectedAddress = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgBack = findViewById(R.id.imgBack);
        editSchoolName = findViewById(R.id.editSchoolName);
        editLocation = findViewById(R.id.editLocation);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        btnSaveChanges = findViewById(R.id.btnSaveChanges); // Save Changes button


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Profile Activity
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

        // Initialize ActivityResultLauncher for MapSelectionActivity 
        mapSelectionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            selectedLat = data.getDoubleExtra("lat", 0.0);
                            selectedLng = data.getDoubleExtra("lng", 0.0);
                            selectedAddress = data.getStringExtra("address");

                            // Update the editLocation EditText with the selected address
                            if (selectedAddress != null && !selectedAddress.isEmpty()) {
                                editLocation.setText(selectedAddress);
                            } else {
                                // Fallback to Lat/Lng if address is not available
                                editLocation.setText("Lat: " + selectedLat + ", Lng: " + selectedLng);
                            }
                            Toast.makeText(this, "Location selected: " + selectedAddress, Toast.LENGTH_SHORT).show();
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(this, "Location selection cancelled.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //  EditLocation Click Listener 
        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, MapSelectionActivity.class);
                mapSelectionLauncher.launch(intent);
            }
        });

        //Make editLocation non-editable by keyboard but clickable 
        editLocation.setFocusable(false); // Prevents keyboard from popping up
        editLocation.setClickable(true);  // Makes it respond to clicks
        
        loadProfileData();
    }

    private void loadProfileData() {
    }

}