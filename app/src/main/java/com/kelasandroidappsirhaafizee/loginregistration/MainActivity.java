package com.kelasandroidappsirhaafizee.loginregistration;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String name = getIntent().getStringExtra("username");

        TextView welcome= findViewById(R.id.welcome);
        welcome.setText("Welcome,"+name+"!");
    }
}