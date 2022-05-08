package com.example.decorationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddProductsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProductsActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAtuh;
    private ArrayList<Product> Items;
    private ProductAdapter adapter;
    private FirebaseFirestore firestore;

    private NotificationHandler handler;

    EditText nameET;
    RatingBar starsRB;
    EditText amountET;
    EditText priceET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        mAtuh = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG, "Bejelentkezett felhasználó");
        } else {
            Log.d(LOG_TAG, "Nincs bejelentkezett felhasználó");
            finish();
        }

        Items = new ArrayList<>();
        adapter = new ProductAdapter(this, Items);
        firestore = FirebaseFirestore.getInstance();

        nameET = findViewById(R.id.addName);
        starsRB = findViewById(R.id.addStars);
        amountET = findViewById(R.id.addAmount);
        priceET = findViewById(R.id.addPrice);

        handler = new NotificationHandler(this);

    }


    public void addProduct(View view) {
        String name = nameET.getText().toString();
        String stars = Float.toString(starsRB.getRating());
        String amount = amountET.getText().toString();
        String price = priceET.getText().toString();
        if(!name.isEmpty() && !stars.isEmpty() && !price.isEmpty() && !amount.isEmpty()){
            float starsFloat = 0;
            int amountInt = 0;
            int priceInt = 0;
            try {
                starsFloat = Float.parseFloat(stars);
                priceInt = Integer.parseInt(price);
                amountInt = Integer.parseInt(amount);
            }catch (Exception e){
                return;
            }
            Product prod = new Product(name, amountInt, starsFloat,priceInt);
            firestore.collection("Products").add(prod).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.i(LOG_TAG,"Sikeres hozzáadás");
                    handler.send(name);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(LOG_TAG,"Sikertelen hozzáadás");
                }
            });
        }
    }

    public void back(View view){
        finish();
    }
}