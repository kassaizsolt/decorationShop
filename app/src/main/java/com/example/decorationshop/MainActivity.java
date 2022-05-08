package com.example.decorationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 77;

    EditText emailET;
    EditText passwordET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.etEmail);
        passwordET = findViewById(R.id.etPassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG, "Sikeres bejelentkezés");
                        products();
                    } else {
                        Log.d(LOG_TAG, "Nincs ilyen felhasználó");
                        Toast.makeText(MainActivity.this, "Nincs ilyen felhasználó" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void login2(View view) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG, "Sikeres bejelentkezés");
                        addProducts();
                    } else {
                        Log.d(LOG_TAG, "Nincs ilyen felhasználó");
                        Toast.makeText(MainActivity.this, "Nincs ilyen felhasználó" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void products(){
        Intent prodint = new Intent(this, ProductsActivity.class);
        startActivity(prodint);
    }

    private void addProducts(){
        Intent prodint = new Intent(this, AddProductsActivity.class);
        startActivity(prodint);
    }

    public void registration(View view) {
        Intent regint = new Intent(this, RegistrationActivity.class);
        regint.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(regint);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", emailET.getText().toString());
        editor.apply();
    }
}