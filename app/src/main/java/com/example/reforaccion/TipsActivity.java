package com.example.reforaccion;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tips_activity);
        returnTipsButtonListener();
    }

    private void returnTipsButtonListener(){
        ImageButton returnTipsButton = findViewById(R.id.returnTipsButton);
        returnTipsButton.setOnClickListener(v -> finish());
    }
}