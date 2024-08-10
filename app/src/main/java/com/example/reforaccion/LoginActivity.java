package com.example.reforaccion;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reforaccion.helpers.Encrypt;
import com.example.reforaccion.models.User;
import com.example.reforaccion.models.UserApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextInputEditText cellphone;
    TextInputEditText password;
    User fetchedUser;
    UserApplication appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        initiateVars();
        registerButtonListener();
        loginButtonListener();
    }

    private void initiateVars(){
        cellphone = findViewById(R.id.cellphoneLoginInput);
        password = findViewById(R.id.passwordLoginInput);
    }

    private void registerButtonListener(){
        Button registerButton = findViewById(R.id.registerLoginButton);
        registerButton.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
    }

    private void loginButtonListener(){
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {

            //Creates an Context User
            appUser = (UserApplication) getApplicationContext().getClass().cast(appUser);
            appUser = new UserApplication();

            if(!Objects.requireNonNull(cellphone.getText()).toString().isEmpty() && !Objects.requireNonNull(password.getText()).toString().isEmpty()){
                DocumentReference docRef = db.collection("users").document(cellphone.getText().toString());
                docRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            fetchedUser = document.toObject(User.class);
                            boolean isPasswordCorrect = Encrypt.validateEncryptedPassword(password.getText().toString(), fetchedUser.getPassword());
                            if(isPasswordCorrect){
                                //Redirects to Main page and sets the global user to the db fetched user
                                appUser.setUser(fetchedUser);
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }else
                                Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Login got failed with ", task.getException());
                    }
                });
            }else
                Toast.makeText(getApplicationContext(), "Ingrese el celular y contraseña para ingresar", Toast.LENGTH_SHORT).show();
        });
    }
}