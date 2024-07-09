package com.example.reforaccion;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_activity);
        registerUserButtonListener();
        returnButtonListener();
    }

    private void registerUserButtonListener(){
        Button registerUserButton = findViewById(R.id.registerUserButton);
        registerUserButton.setOnClickListener(v -> System.out.println("Registrando usuario"));
    }

    private void returnButtonListener(){
        ImageButton returnButton = findViewById(R.id.returnRegisterButton);
        returnButton.setOnClickListener(v -> finish());
    }
}