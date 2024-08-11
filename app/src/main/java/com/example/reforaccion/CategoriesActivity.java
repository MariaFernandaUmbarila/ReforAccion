package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reforaccion.models.UserApplication;


public class CategoriesActivity extends AppCompatActivity {

    UserApplication currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.categories_activity);
        currentUser = (UserApplication) getApplicationContext();
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
            Intent i;
            if(currentUser.user != null)
                i = new Intent(CategoriesActivity.this, MainActivity.class);
            else
                i = new Intent(CategoriesActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}