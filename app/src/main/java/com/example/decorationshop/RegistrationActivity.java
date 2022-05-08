package com.example.decorationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = RegistrationActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 77;

    EditText userEmailET;
    EditText passwordET;
    EditText passwordAgainET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if(secret_key != 77){
            finish();
        }
        userEmailET = findViewById(R.id.userEmail);
        passwordET = findViewById(R.id.password);
        passwordAgainET = findViewById(R.id.rePassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");

        userEmailET.setText(email);

        mAuth = FirebaseAuth.getInstance();


    }

    public void registration(View view) {
        String email = userEmailET.getText().toString();
        String password = passwordET.getText().toString();
        String rePassword = passwordAgainET.getText().toString();

        if(!email.isEmpty() && !password.isEmpty() && !rePassword.isEmpty()){
            if(!password.equals(rePassword)){
                Log.e(LOG_TAG, "Nem egyezik meg a két jelszó");
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG, "Sikeres regisztrálás");
                        finish();
                    } else {
                        Log.d(LOG_TAG, "Sikertelen regisztrálás");
                        Toast.makeText(RegistrationActivity.this, "Sikertelen regisztrálás: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    public void cancel(View view) {
        finish();
    }

    private void products(){
        Intent prodint = new Intent(this, ProductsActivity.class);
        startActivity(prodint);

    }
}