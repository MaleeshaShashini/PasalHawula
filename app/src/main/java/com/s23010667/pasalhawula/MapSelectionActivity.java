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

public class MapSelectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLatLng;
    private Marker currentMarker;
    private Button btnSelectLocation;
    private FusedLocationProviderClient locationClient;

    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        btnSelectLocation = findViewById(R.id.btnSelectLocation);
        locationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnSelectLocation.setOnClickListener(v -> {
            if (selectedLatLng != null) {
                returnLocation();
            } else {
                Toast.makeText(this, "Please select a location.", Toast.LENGTH_SHORT).show();
            }
        });

        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(latLng -> {
            if (currentMarker != null) currentMarker.remove();
            currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected"));
            selectedLatLng = latLng;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
                }
            });
        }
    }

    private void returnLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String address = "";

        try {
            List<Address> list = geocoder.getFromLocation(selectedLatLng.latitude, selectedLatLng.longitude, 1);
            if (!list.isEmpty()) {
                address = list.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            address = "Lat: " + selectedLatLng.latitude + ", Lng: " + selectedLatLng.longitude;
        }

        Intent result = new Intent();
        result.putExtra("lat", selectedLatLng.latitude);
        result.putExtra("lng", selectedLatLng.longitude);
        result.putExtra("address", address);
        setResult(RESULT_OK, result);
        finish();
    }
}
