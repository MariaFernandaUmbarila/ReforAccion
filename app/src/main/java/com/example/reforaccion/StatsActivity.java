package com.example.reforaccion;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.statistics_activity);
        returnStatsButtonListener();
    }

    private void returnStatsButtonListener(){
        ImageButton returnUserButton = findViewById(R.id.returnStatsButton);
        returnUserButton.setOnClickListener(v -> finish());
    }
}