package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtAlreadyHaveAccountLogin;
    private Button registerButton;
    DatabaseHelper myDb;
    EditText editSchoolName, editContactPerson, editEmail, editPhone, editPassword, editPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.btnRegister);
        txtAlreadyHaveAccountLogin = findViewById(R.id.txtAlreadyHaveAccountLogin);
        myDb = new DatabaseHelper(this);

        editSchoolName = findViewById(R.id.editSchoolName);
        editContactPerson = findViewById(R.id.editContactPerson);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        editPasswordConfirm = findViewById(R.id.editPasswordConfirm);

        //Navigation to login page
        txtAlreadyHaveAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Register button action
        registerButton.setOnClickListener(v -> {
            String schoolName = editSchoolName.getText().toString().trim();
            String contactPerson = editContactPerson.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String password = editPassword.getText().toString();
            String confirmPassword = editPasswordConfirm.getText().toString();

            boolean isDataInserted= myDb.insertData(
                    editSchoolName.getText().toString(),
                    editContactPerson.getText().toString(),
                    editEmail.getText().toString(),
                    editPhone.getText().toString(),
                    editPassword.getText().toString(),
                    editPasswordConfirm.getText().toString()
            );

            // Check for empty fields
            if(schoolName.isEmpty() || contactPerson.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(RegisterActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate phone number
            if(!phone.matches("\\d{10}")) {
                Toast.makeText(RegisterActivity.this, "Enter valid 10-digit mobile no.", Toast.LENGTH_SHORT).show();
                return;
            }

            // heck if passwords match
            if(!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Password and Confirm Password do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Password length check
            if(password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password too short! Minimum 6 characters required.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert data into database
            if(isDataInserted == true) {
                Toast.makeText(RegisterActivity.this, "You’ve successfully registered.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(RegisterActivity.this, "Something went wrong. Please try again ", Toast.LENGTH_LONG).show();
            }
        });
    }
}

