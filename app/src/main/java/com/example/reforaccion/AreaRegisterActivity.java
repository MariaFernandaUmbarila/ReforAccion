package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class AreaRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.area_register_activity);
        areaRegisterButtonListener();
        returnAreaButtonListener();
    }

    private void areaRegisterButtonListener(){
        Button areaRegister = findViewById(R.id.registerAreaButton);
        areaRegister.setOnClickListener(v -> {
            Intent i = new Intent(AreaRegisterActivity.this, MainActivity.class);
            startActivity(i);
        });
    }

    private void returnAreaButtonListener(){
        ImageButton returnAreaButton = findViewById(R.id.returnAreaButton);
        returnAreaButton.setOnClickListener(v -> finish());
    }
}