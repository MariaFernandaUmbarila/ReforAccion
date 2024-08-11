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
import com.example.reforaccion.models.Plant;
import com.example.reforaccion.models.UserApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Objects;


public class PlantRegisterActivity extends AppCompatActivity {

    TextInputEditText quantity;
    TextInputEditText individualCost;
    TextInputEditText datePlanted;
    UserApplication currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Plant newPlant;
    ArrayList<Boolean> validations = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.plant_register_activity);
        initiateVars();
        plantRegisterButtonListener();
        returnPlantButtonListener();
    }

    private void initiateVars(){
        quantity = findViewById(R.id.plantsQuantityInput);
        individualCost = findViewById(R.id.individualCostInput);
        datePlanted = findViewById(R.id.monthPlantedInput);
        currentUser = (UserApplication) getApplicationContext();
        for (int i = 0; i < 3; i++) {
            validations.add(false);
        }
    }

    private void plantRegisterButtonListener(){
        Button plantRegister = findViewById(R.id.registerPlantButton);
        plantRegister.setOnClickListener(v -> {

            //Get current user from context and db
            DocumentReference docRef = db.collection("users").document(currentUser.user.getCellphone());

            if(!Objects.requireNonNull(quantity.getText()).toString().isEmpty() && !Objects.requireNonNull(individualCost.getText()).toString().isEmpty()){
                //Checks for the quantity to be more than 0
                if(Integer.parseInt(Objects.requireNonNull(quantity.getText()).toString()) > 0){
                    validations.set(0, true);
                }else Toast.makeText(getApplicationContext(), "Cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();

                //Checks for the individual cost to be more than 0
                if(Double.parseDouble(Objects.requireNonNull(individualCost.getText()).toString()) > 0){
                    validations.set(1, true);
                }else Toast.makeText(getApplicationContext(), "Cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(), "Ingrese datos para continuar", Toast.LENGTH_SHORT).show();

            //Checks for the month to be in the month list
            if(isMonth(Objects.requireNonNull(datePlanted.getText()).toString().trim())){
                validations.set(2, true);
            }else Toast.makeText(getApplicationContext(), "Mes ingresado inválido", Toast.LENGTH_SHORT).show();

            if(validations.stream().allMatch(Boolean::booleanValue)){
                //Creates a new Plant object
                newPlant = new Plant(
                        Integer.parseInt(Objects.requireNonNull(quantity.getText()).toString()),
                        Double.parseDouble(Objects.requireNonNull(individualCost.getText()).toString()),
                        Objects.requireNonNull(datePlanted.getText()).toString()
                );

                //Updates the plant array in db for the current user
                docRef.update("plants", FieldValue.arrayUnion(newPlant))
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getApplicationContext(), "Añadido con éxito", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PlantRegisterActivity.this, MainActivity.class);
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

    private void returnPlantButtonListener(){
        ImageButton returnPlantButton = findViewById(R.id.returnPlantsButton);
        returnPlantButton.setOnClickListener(v -> {
            Intent i;
            if(currentUser.user != null)
                i = new Intent(PlantRegisterActivity.this, CategoriesActivity.class);
            else
                i = new Intent(PlantRegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}