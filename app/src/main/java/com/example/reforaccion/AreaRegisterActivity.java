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
import com.example.reforaccion.models.Area;
import com.example.reforaccion.models.UserApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Objects;


public class AreaRegisterActivity extends AppCompatActivity {

    TextInputEditText zone;
    TextInputEditText area;
    TextInputEditText datePlanted;
    Area newArea;
    UserApplication currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Boolean> validations = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.area_register_activity);
        initiateVars();
        areaRegisterButtonListener();
        returnAreaButtonListener();
    }

    private void initiateVars(){
        zone = findViewById(R.id.zonePlantedInput);
        area = findViewById(R.id.areaPlantedInput);
        datePlanted = findViewById(R.id.monthAreaInput);
        currentUser = (UserApplication) getApplicationContext();
        for (int i = 0; i < 3; i++) {
            validations.add(false);
        }
    }

    private void areaRegisterButtonListener(){
        Button areaRegister = findViewById(R.id.registerAreaButton);
        areaRegister.setOnClickListener(v -> {

            //Get current user from context and db
            DocumentReference docRef = db.collection("users").document(currentUser.user.getCellphone());

            if(!Objects.requireNonNull(zone.getText()).toString().isEmpty()){
                validations.set(0, true);
            }else Toast.makeText(getApplicationContext(), "Ingrese una zona válida", Toast.LENGTH_SHORT).show();

            if(!Objects.requireNonNull(area.getText()).toString().isEmpty()){
                //Checks for the area to be more than 0
                if(Double.parseDouble(Objects.requireNonNull(area.getText()).toString()) > 0){
                    validations.set(1, true);
                }else Toast.makeText(getApplicationContext(), "Área debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(), "Ingrese datos para continuar", Toast.LENGTH_SHORT).show();

            if(isMonth(Objects.requireNonNull(datePlanted.getText()).toString().trim())){
                validations.set(2, true);
            }else Toast.makeText(getApplicationContext(), "Mes ingresado inválido", Toast.LENGTH_SHORT).show();

            if(validations.stream().allMatch(Boolean::booleanValue)){
                //Creates an area object
                newArea = new Area(
                    Objects.requireNonNull(zone.getText()).toString(),
                    Double.parseDouble(Objects.requireNonNull(area.getText()).toString()),
                    Objects.requireNonNull(datePlanted.getText()).toString()
                );
                //Updates the plant array in db for the current user
                docRef.update("areas", FieldValue.arrayUnion(newArea))
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getApplicationContext(), "Añadido con éxito", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AreaRegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getApplicationContext(), "Hubo un problema al añadir el registro", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Plant register failed: " + e.getMessage());
                        });

            }else
                Toast.makeText(getApplicationContext(), "Valide todos los campos antes de continuar", Toast.LENGTH_SHORT).show();
        });
    }

    private static boolean isMonth(String input) {

        String[] months = { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
        for (String month : months) {
            if (month.equalsIgnoreCase(input))
                return true;
        }
        return false;
    }

    private void returnAreaButtonListener(){
        ImageButton returnAreaButton = findViewById(R.id.returnAreaButton);
        returnAreaButton.setOnClickListener(v -> {
            Intent i = new Intent(AreaRegisterActivity.this, CategoriesActivity.class);
            startActivity(i);
        });
    }
}