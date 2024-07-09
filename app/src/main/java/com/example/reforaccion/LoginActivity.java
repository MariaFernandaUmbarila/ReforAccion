package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        registerButtonListener();
        loginButtonListener();
    }

    private void registerButtonListener(){
        Button registerButton = findViewById(R.id.registerLoginButton);
        registerButton.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
    }

    private void loginButtonListener(){
        Button registerButton = findViewById(R.id.loginButton);
        registerButton.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}