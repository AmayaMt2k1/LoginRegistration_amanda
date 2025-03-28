package com.kelasandroidappsirhaafizee.loginregistration;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignupActivity extends AppCompatActivity {

    EditText nameInput, usernameInput, passwordInput, confirmPasswordInput;
    Button signupButton;
    TextView loginText;
    TextView requirementLength, requirementLetter, requirementNumber, requirementSymbol;
    ImageView tickLength, tickLetter, tickNumber, tickSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginText = findViewById(R.id.loginText);
        nameInput = findViewById(R.id.name);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        confirmPasswordInput = findViewById(R.id.confirm_password);

        signupButton = findViewById(R.id.singupButton);

        requirementLength = findViewById(R.id.requirementLength);
        requirementLetter = findViewById(R.id.requirementLetter);
        requirementNumber = findViewById(R.id.requirementNumber);
        requirementSymbol = findViewById(R.id.requirementSymbol);

        tickLength = findViewById(R.id.tickLength);
        tickLetter = findViewById(R.id.tickLetter);
        tickNumber = findViewById(R.id.tickNumber);
        tickSymbol = findViewById(R.id.tickSymbol);

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        signupButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser () {
        String name = nameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (name.isEmpty() | username.isEmpty() | password.isEmpty() | confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try (Connection conn = DatabaseHelper.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO loginRegisterDB.dbo.Users (name, username, password, confirm_Password)" + " VALUES (?, ?, ?, ?) ")) {
                stmt.setString(1,name);
                stmt.setString(2,username);
                stmt.setString(3,password);
                stmt.setString(4,confirmPassword);

                int rowsAffected = stmt.executeUpdate();
                runOnUiThread(() -> {
                    if (rowsAffected > 0) {
                        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void validatePassword(String password) {
        if (password.length() >= 8) {
            tickLength.setVisibility(ImageView.VISIBLE);
            requirementLength.setTextColor(getResources().getColor(R.color.green));
        } else {
            tickLength.setVisibility(ImageView.GONE);
            requirementLength.setTextColor(getResources().getColor(R.color.gray));
        }
        if (password.matches(".*[A-Za-z]*.")) {
            tickLetter.setVisibility(ImageView.VISIBLE);
            requirementLetter.setTextColor(getResources().getColor(R.color.green));
        } else {
            tickLength.setVisibility(ImageView.GONE);
            requirementLetter.setTextColor(getResources().getColor(R.color.gray));
        }
        if (password.matches(".*\\d.*")) {
            tickNumber.setVisibility(ImageView.VISIBLE);
            requirementNumber.setTextColor(getResources().getColor(R.color.green));
        } else {
            tickNumber.setVisibility(ImageView.GONE);
            requirementNumber.setTextColor(getResources().getColor(R.color.gray));
        }
        if (password.matches(".*[!@#$%^&*()].*")) {
            tickSymbol.setVisibility(ImageView.VISIBLE);
            requirementSymbol.setTextColor(getResources().getColor(R.color.green));
        } else {
            tickSymbol.setVisibility(ImageView.GONE);
            requirementSymbol.setTextColor(getResources().getColor(R.color.gray));
        }
    }
}