package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_activity);
        logoutButtonListener();
        returnUserButtonListener();
    }

    private void logoutButtonListener(){
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent i = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void returnUserButtonListener(){
        ImageButton returnUserButton = findViewById(R.id.returnUserButton);
        returnUserButton.setOnClickListener(v -> finish());
    }
}