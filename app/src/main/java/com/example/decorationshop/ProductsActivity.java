package com.example.decorationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProductsActivity.class.getName();

    private FirebaseUser user;
    private FirebaseAuth mAtuh;

    private RecyclerView ReView;
    private ArrayList<Product> Items;
    private ProductAdapter adapter;

    private FirebaseFirestore firestore;

    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mAtuh = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG, "Bejelentkezett felhasználó");
        } else {
            Log.d(LOG_TAG, "Nincs bejelentkezett felhasználó");
            finish();
        }

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridNumber = 2;
        } else {
            gridNumber = 1;
        }

        ReView = findViewById(R.id.reView);
        ReView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        Items = new ArrayList<>();

        adapter = new ProductAdapter(this, Items);

        firestore = FirebaseFirestore.getInstance();

        listAll();

    }

    private void listAll(){
        firestore.collection("Products").orderBy("name").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){

                                Items.add(new Product(
                                                document.getId(),
                                                document.getData().get("name").toString(),
                                                Integer.parseInt(document.getData().get("amount").toString()),
                                                Float.parseFloat(document.getData().get("stars").toString()),
                                                Integer.parseInt(document.getData().get("price").toString())
                                        )
                                );
                            }

                            ReView.setAdapter(adapter);
                        }else{
                            Log.i(LOG_TAG,"Sikertelen listázás");
                        }
                    }
                });
    }

    public void back(View view) {
        finish();
    }
}