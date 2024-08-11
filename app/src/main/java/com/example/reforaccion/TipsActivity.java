package com.example.reforaccion;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reforaccion.models.Tips;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TipsActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button firstTip;
    Button secondTip;
    List<Tips> tips;
    int tipsQty = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tips_activity);
        initiateVars();
        getRandomDocuments(tipsQty);
        returnTipsButtonListener();
    }

    private void initiateVars(){
        firstTip = findViewById(R.id.tipAButton);
        secondTip = findViewById(R.id.tipBButton);
        tips = new ArrayList<>();
    }

    private void getRandomDocuments(int numberOfDocuments) {
        CollectionReference collectionRef = db.collection("tips");
        collectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> documents = new ArrayList<>(task.getResult().getDocuments());
                //Check if there is enough documents
                if (documents.size() < numberOfDocuments) {
                    Toast.makeText(getApplicationContext(), "No pudo obtenerse la información", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No hay documentos disponibles");
                    return;
                }
                //Select randon tips documents
                Collections.shuffle(documents);
                List<DocumentSnapshot> randomDocuments = documents.subList(0, numberOfDocuments);
                for (DocumentSnapshot document : randomDocuments) {
                    //Adds the tip to the Map object
                    Tips tip = document.toObject(Tips.class);
                    tips.add(tip);
                    Log.d(TAG, "Tips: " + tips);
                }
                renderTips(tips);
            } else
                Toast.makeText(getApplicationContext(), "No pudo obtenerse la información", Toast.LENGTH_SHORT).show();

        });
    }

    private void renderTips(List<Tips> tipsList){
        firstTip.setText(tipsList.get(0).getDescription());
        secondTip.setText(tipsList.get(1).getDescription());
    }

    private void returnTipsButtonListener(){
        ImageButton returnTipsButton = findViewById(R.id.returnTipsButton);
        returnTipsButton.setOnClickListener(v -> {
            Intent i = new Intent(TipsActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}