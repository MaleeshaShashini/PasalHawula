package com.s23010667.pasalhawula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.textfield.TextInputEditText;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;

public class MapSelectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLatLng; // Stores the LatLng of the selected location
    private Marker currentMarker;
    private Button btnSelectLocation;
    private String selectedAddressText; // Stores the address string of the selected location
    private FusedLocationProviderClient locationClient;
    private TextInputEditText editSearchLocation;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        btnSelectLocation = findViewById(R.id.btnSelectLocation);
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        editSearchLocation = findViewById(R.id.editSearchLocation);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Error loading map fragment.", Toast.LENGTH_SHORT).show();
        }

        // Set up listener for the "Select This Location" button
        btnSelectLocation.setOnClickListener(v -> {
            if (selectedLatLng != null) {
                // Before returning, update the TextInputEditText with the selected address
                if (selectedAddressText != null && !selectedAddressText.isEmpty()) {
                    editSearchLocation.setText(selectedAddressText);
                } else {
                    // If address not found by geocoder, show lat/lng
                    editSearchLocation.setText("Lat: " + selectedLatLng.latitude + ", Lng: " + selectedLatLng.longitude);
                }
                returnLocation(); // This will finish the activity and pass data back
            } else {
                Toast.makeText(this, "Please select a location on the map first.", Toast.LENGTH_SHORT).show();
            }
        });

        editSearchLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard(); // Hide keyboard after search
                    performLocationSearch(editSearchLocation.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        checkPermission();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editSearchLocation.getWindowToken(), 0);
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Set an initial default location ( Colombo, Sri Lanka)
        LatLng colombo = new LatLng(6.9271, 79.8612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombo, 10));

        mMap.setOnMapClickListener(latLng -> {
            if (currentMarker != null) currentMarker.remove(); // Remove previous marker

            // Add new marker
            currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected"));
            selectedLatLng = latLng;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            // Geocode the tapped location to get its address and update selectedAddressText
            updateAddressFromLatLng(latLng);
        });

        // Enable "My Location" layer if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            // Get current location and move camera to it
            locationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
                }
            });
        }
    }

    private void performLocationSearch(String addressString) {
        if (addressString.isEmpty()) {
            Toast.makeText(MapSelectionActivity.this, "Please enter an address to search.", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(addressString, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                if (currentMarker != null) currentMarker.remove(); // Clear previous marker
                currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(addressString));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                selectedLatLng = latLng; // Update selectedLatLng to the searched location
                selectedAddressText = addressString; // Store the address directly

                Toast.makeText(this, "Address Found: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Location not found for the given address.", Toast.LENGTH_LONG).show();
                selectedLatLng = null; // Clear selected location if not found
                selectedAddressText = null;
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error during geocoding: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            selectedLatLng = null; // Clear selected location on error
            selectedAddressText = null;
        }
    }

    private void updateAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (list != null && !list.isEmpty()) {
                selectedAddressText = list.get(0).getAddressLine(0);
            } else {
                selectedAddressText = "Lat: " + latLng.latitude + ", Lng: " + latLng.longitude; // Fallback
            }
        } catch (IOException e) {
            e.printStackTrace();
            selectedAddressText = "Lat: " + latLng.latitude + ", Lng: " + latLng.longitude; // Fallback on error
        }
    }

    private void returnLocation() {
        Intent result = new Intent();
        result.putExtra("lat", selectedLatLng.latitude);
        result.putExtra("lng", selectedLatLng.longitude);
        result.putExtra("address", selectedAddressText); // Pass the stored address string
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                        locationClient.getLastLocation().addOnSuccessListener(location -> {
                            if (location != null) {
                                LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));

                            }
                        });
                    }
                }
                Toast.makeText(this, "Location permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied. Cannot show current location.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
