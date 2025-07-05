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

import java.io.File; // Import for File
import java.io.IOException; // Import for IOException
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.ContextCompat; // For ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider; // Import for FileProvider

public class PhysicalFormFragment extends Fragment {

    // UI Elements from your XML layout
    private TextInputEditText etLocation;
    private TextInputLayout inputLayoutLocation; // To get the end icon click listener
    private ImageView imgUploadIcon;
    private Button btnTakePhoto;
    private Uri currentPhotoUri;// Stores the URI of the captured photo
    private ActivityResultLauncher<Uri> takePictureLauncher;// For launching camera and getting picture
    private ActivityResultLauncher<Intent> mapSelectionLauncher;    // For launching map selection activity
    private ActivityResultLauncher<String[]> requestPermissionLauncher; // For requesting runtime permissions

    public PhysicalFormFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register launcher for map selection activity result
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

        // Register launcher for taking a picture with the camera
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success) {
                        if (currentPhotoUri != null) {
                            imgUploadIcon.setImageURI(currentPhotoUri);
                            Toast.makeText(getContext(), "Photo Captured!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Photo capture failed.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            Boolean cameraGranted = result.getOrDefault(Manifest.permission.CAMERA, false);
            Boolean writeStorageGranted = result.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
            Boolean readMediaImagesGranted = result.getOrDefault(Manifest.permission.READ_MEDIA_IMAGES, false); // For API 33+

            // Check if at least Camera and appropriate storage permission is granted based on Android version
            boolean allPermissionsGranted = false;
            if (cameraGranted != null && cameraGranted) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {// Android 13 (API 33) and above
                    if (readMediaImagesGranted != null && readMediaImagesGranted) {
                        allPermissionsGranted = true;
                    }
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {// Android 10 (API 29) to Android 12 (API 32)
                    allPermissionsGranted = true;
                } else {// Android 9 (API 28) and below
                    if (writeStorageGranted != null && writeStorageGranted) {
                        allPermissionsGranted = true;
                    }
                }
            }

            if (allPermissionsGranted) {
                // Permissions granted, proceed with taking photo
                currentPhotoUri = createImageFileUri(); // Create a file URI to save the photo
                if (currentPhotoUri != null) {
                    takePictureLauncher.launch(currentPhotoUri);// Launch the camera
                } else {
                    Toast.makeText(getContext(), "Failed to create image file for photo.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Permissions not granted, show a message
                Toast.makeText(getContext(), "Camera and storage permissions are required to take photos.", Toast.LENGTH_LONG).show();
            }
        });
 }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_physical, container, false);

        // Initialize UI elements
        etLocation = view.findViewById(R.id.etLocation);
        inputLayoutLocation = view.findViewById(R.id.inputLayoutLocation); // Get the TextInputLayout for its end icon listener
        imgUploadIcon = view.findViewById(R.id.imgUploadIcon);
        btnTakePhoto = view.findViewById(R.id.btnTakePhoto);

        // Set OnClickListener for the end icon of the Location TextInputLayout
        if (inputLayoutLocation != null) {
            inputLayoutLocation.setEndIconOnClickListener(v -> {
                Intent intent = new Intent(getContext(), MapSelectionActivity.class);
                mapSelectionLauncher.launch(intent);
            });
        }

        // Set OnClickListener for the "Take Photo" button
        btnTakePhoto.setOnClickListener(v -> {
            if (checkCameraAndStoragePermissions()) {// Check if necessary permissions are granted
                currentPhotoUri = createImageFileUri();// Create URI for the new photo file
                if (currentPhotoUri != null) {
                    takePictureLauncher.launch(currentPhotoUri);// Launch camera to take photo
                } else {
                    Toast.makeText(getContext(), "Failed to create image file for photo.", Toast.LENGTH_SHORT).show();
                }
            } else {
                requestCameraAndStoragePermissions();
            }
        });

        return view;
    }

    // Creates a temporary file URI for storing the captured image
    private Uri createImageFileUri() {
        String fileName = "JPEG_" + System.currentTimeMillis() + "_";
        // Get the app's private external pictures directory
        File storageDir = requireContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(fileName, ".jpg", storageDir);// Create a new temporary image file
            return FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getApplicationContext().getPackageName() + ".fileprovider",
                    imageFile
            );
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating image file.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // Checks if Camera and appropriate Storage permissions are granted
    private boolean checkCameraAndStoragePermissions() {
        boolean cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        boolean storagePermission = false;
        // Check storage permission based on Android version
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            storagePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {
            storagePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            storagePermission = true;
        }
        return cameraPermission && storagePermission;

    }

        // Requests Camera and Storage permissions from the user
        private void requestCameraAndStoragePermissions () {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {

                requestPermissionLauncher.launch(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_MEDIA_IMAGES
                });
            } else if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {

                requestPermissionLauncher.launch(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
            } else {
                requestPermissionLauncher.launch(new String[]{
                        Manifest.permission.CAMERA
                });
            }
        }

}