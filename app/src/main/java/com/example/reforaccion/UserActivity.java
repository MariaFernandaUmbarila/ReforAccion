package com.example.reforaccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reforaccion.models.UserApplication;


public class UserActivity extends AppCompatActivity {

    UserApplication currentUser;
    TextView usernameText;
    TextView cellphoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_activity);
        initiateVars();
        renderUsernameInfo();
        logoutButtonListener();
        returnUserButtonListener();
    }

    private void initiateVars(){
        currentUser = (UserApplication) getApplicationContext();
        usernameText = findViewById(R.id.usernameText);
        cellphoneText = findViewById(R.id.cellpjoneUserText);
    }

    private void renderUsernameInfo(){
        usernameText.setText(currentUser.user.getUserName());
        cellphoneText.setText(currentUser.user.getCellphone());
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
        returnUserButton.setOnClickListener(v -> {
            Intent i = new Intent(UserActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}