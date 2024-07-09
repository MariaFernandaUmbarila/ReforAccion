package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_activity);
        userPageButtonListener();
        statsButtonListener();
        categoriesButtonListnener();
        tipsButtonListener();
    }

    private void userPageButtonListener(){
        ImageButton userButton = findViewById(R.id.personalInfoButton);
        userButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, UserActivity.class);
            startActivity(i);
        });
    }

    private void statsButtonListener(){
        Button statsButton = findViewById(R.id.statsButton);
        statsButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, StatsActivity.class);
            startActivity(i);
        });
    }

    private void categoriesButtonListnener(){
        Button categoriesButton = findViewById(R.id.categoriesButton);
        categoriesButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CategoriesActivity.class);
            startActivity(i);
        });
    }

    private void tipsButtonListener(){
        Button tipsButton = findViewById(R.id.tipsButton);
        tipsButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(i);
        });
    }
}