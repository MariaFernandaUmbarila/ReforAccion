package com.example.reforaccion;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_activity);
        returnUserButtonListener();
    }

    private void returnUserButtonListener(){
        ImageButton returnUserButton = findViewById(R.id.returnUserButton);
        returnUserButton.setOnClickListener(v -> finish());
    }
}