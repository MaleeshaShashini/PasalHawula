package com.s23010667.pasalhawula;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity; // For RESULT_OK
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText; // Corrected import
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class PhysicalFormFragment extends Fragment {

    // UI Elements from your XML layout
    private TextInputEditText etLocation;
    private TextInputLayout inputLayoutLocation; // To get the end icon click listener

    // For location selection
    private ActivityResultLauncher<Intent> mapSelectionLauncher;

    public PhysicalFormFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapSelectionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        double lat = data.getDoubleExtra("selected_location_lat", 0.0);
                        double lng = data.getDoubleExtra("selected_location_lng", 0.0);
                        String address = data.getStringExtra("selected_location_address");

                        if (address != null && !address.isEmpty()) {
                            etLocation.setText(address);
                        } else {
                            // Fallback to coordinates if address is not available
                            etLocation.setText(String.format(Locale.getDefault(), "Lat: %.4f, Lng: %.4f", lat, lng));
                        }
                        Toast.makeText(getContext(), "Location Selected: " + address, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Location selection cancelled or failed.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_physical, container, false); // <--- Make sure this is your fragment's XML file name

        // Initialize UI elements
        etLocation = view.findViewById(R.id.etLocation);
        inputLayoutLocation = view.findViewById(R.id.inputLayoutLocation); // Get the TextInputLayout for its end icon listener

       // Set OnClickListener for the end icon of the Location TextInputLayout
        if (inputLayoutLocation != null) {
            inputLayoutLocation.setEndIconOnClickListener(v -> {
                Intent intent = new Intent(getContext(), MapSelectionActivity.class);
                mapSelectionLauncher.launch(intent);
            });
        }
         return view;
    }

}