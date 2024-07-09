package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class PlantRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.plant_register_activity);
        plantRegisterButtonListener();
        returnPlantButtonListener();
    }

    private void plantRegisterButtonListener(){
        Button plantRegister = findViewById(R.id.registerPlantButton);
        plantRegister.setOnClickListener(v -> {
            Intent i = new Intent(PlantRegisterActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void returnPlantButtonListener(){
        ImageButton returnPlantButton = findViewById(R.id.returnPlantsButton);
        returnPlantButton.setOnClickListener(v -> finish());
    }
}