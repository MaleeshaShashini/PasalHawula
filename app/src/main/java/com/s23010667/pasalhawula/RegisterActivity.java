package com.s23010667.pasalhawula;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtAlreadyHaveAccountLogin;
    private Button registerButton;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registerButton = findViewById(R.id.btnRegister);
        txtAlreadyHaveAccountLogin = findViewById(R.id.txtAlreadyHaveAccountLogin);
        myDb = new DatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        //Navigation to login page
        txtAlreadyHaveAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
            }
    }

