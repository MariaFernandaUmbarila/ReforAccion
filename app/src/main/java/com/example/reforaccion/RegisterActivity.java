package com.example.reforaccion;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reforaccion.helpers.Encrypt;
import com.example.reforaccion.helpers.Reflection;
import com.example.reforaccion.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user;
    TextInputEditText cellphone;
    TextInputEditText name;
    TextInputEditText password;
    TextInputEditText confirmPassword;
    CheckBox termsAccepted;
    ArrayList<Boolean> validations = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_activity);

        initiateVars();
        registerUserButtonListener();
        returnButtonListener();
    }

    private void initiateVars(){
        cellphone = findViewById(R.id.cellphoneInput);
        name = findViewById(R.id.nameInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.passwordConfirmInput);
        termsAccepted = findViewById(R.id.termsCheckBox);
        for (int i = 0; i < 4; i++) {
            validations.add(false);
        }
    }

    private void registerUserButtonListener(){

        Button registerUserButton = findViewById(R.id.registerUserButton);
        registerUserButton.setOnClickListener(v -> {

            //Regex validation for the cellphone to have exactly 10 numeric digits
            if(!Objects.requireNonNull(cellphone.getText()).toString().matches("^\\d{10}$")){
                Toast.makeText(getApplicationContext(), "El celular debe tener diez dígitos numéricos", Toast.LENGTH_SHORT).show();
            }else validations.set(0, true);

            //Regex validation for the name to hace between 5 and 255 chars with no especial chars except for Ñ and spaces
            if(!Objects.requireNonNull(name.getText()).toString().matches("^[A-Za-zñÑ ]{5,255}$")){
                Toast.makeText(getApplicationContext(), "El nombre debe tener entre 5 y 255 caracteres sin comas ni tildes", Toast.LENGTH_SHORT).show();
            }else validations.set(1, true);

            //Regex validation for the password to have betwwen 8 and 255 digits with one special char minumun
            if(!Objects.requireNonNull(password.getText()).toString().matches("^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?])[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?]{8,255}$")){
                Toast.makeText(getApplicationContext(), "La contraseña debe tener entre 8 caracteres y un caracter especial", Toast.LENGTH_SHORT).show();
            }else validations.set(2, true);

            //String comparisson for the two passwords to be the same
            if(!password.getText().toString().equals(Objects.requireNonNull(confirmPassword.getText()).toString())){
                Toast.makeText(getApplicationContext(), "Las contraseñas no coindicen", Toast.LENGTH_SHORT).show();
            }else validations.set(3, true);

            if(validations.stream().allMatch(Boolean::booleanValue)){

                //Creates a new User object
                user = new User(
                    cellphone.getText().toString(),
                    name.getText().toString(),
                    Encrypt.encryptPassword(password.getText().toString()),
                    new Date(),
                    termsAccepted.isChecked()
                );

                //Adds the User object as a document to Firestore collection
                try {
                    db.collection("users")
                    .add(Reflection.objectToMap(user))
                    .addOnSuccessListener(doc -> {
                        Log.d(TAG, "DocumentSnapshot successfully written!" + doc);
                        Toast.makeText(getApplicationContext(), "Usuario creado correctamente", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(getApplicationContext(), "No se pudo crear el usuario, intente de nuevo", Toast.LENGTH_LONG).show();
                    });
                } catch (IllegalAccessException e) {
                    Log.w(TAG, "Error writing document", e);
                    throw new RuntimeException(e);
                }

            }else
                Toast.makeText(getApplicationContext(), "Valide todos los campos antes de continuar", Toast.LENGTH_SHORT).show();

        });
    }

    private void returnButtonListener(){
        ImageButton returnButton = findViewById(R.id.returnRegisterButton);
        returnButton.setOnClickListener(v -> finish());
    }
}