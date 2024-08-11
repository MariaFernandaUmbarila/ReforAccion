package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.categories_activity);
        plantsButtonListener();
        areaButtonListener();
        returnCateButtonListener();
    }

    private void plantsButtonListener(){
        Button plantsButton = findViewById(R.id.plantsCatButton);
        plantsButton.setOnClickListener(v -> {
            Intent i = new Intent(CategoriesActivity.this, PlantRegisterActivity.class);
            startActivity(i);
        });
    }

    private void areaButtonListener(){
        Button areaButton = findViewById(R.id.areasCatButton);
        areaButton.setOnClickListener(v -> {
            Intent i = new Intent(CategoriesActivity.this, AreaRegisterActivity.class);
            startActivity(i);
        });
    }

    private void returnCateButtonListener(){
        ImageButton returnCateButton = findViewById(R.id.returnCateButton);
        returnCateButton.setOnClickListener(v -> {
            Intent i = new Intent(CategoriesActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}