package com.example.decorationshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private static final String LOG_TAG = ProductAdapter.class.getName();
    private ArrayList<Product> data;
    private Context context;
    private int lastPosition = -1;
    private FirebaseFirestore firestore;

    ProductAdapter(Context context, ArrayList<Product> data){
        this.data = data;
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_products, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Product currentItem = data.get(position);

        holder.bindTo(currentItem, position);

        if(holder.getAbsoluteAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition=holder.getAbsoluteAdapterPosition();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView price;
        private TextView amount;
        private RatingBar stars;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTW);
            amount = itemView.findViewById(R.id.amountTW);
            stars = itemView.findViewById(R.id.starsRB);
            price = itemView.findViewById(R.id.priceTW);

        }

        public void bindTo(Product currentItem, int position) {
            title.setTag(currentItem.getId());
            title.setText(currentItem.getName());
            price.setText(Integer.toString(currentItem.getPrice()));
            amount.setText(Integer.toString(currentItem.getAmount()));
            stars.setRating(currentItem.getStars());
            itemView.findViewById(R.id.shopButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = (String)title.getTag();
                    int current = Integer.parseInt(amount.getText().toString());
                    if(current>1){
                        current-=1;

                        currentItem.setAmount(current);
                        update(id,currentItem);

                        notifyItemChanged(position);
                    }else{
                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.delete_animation);
                        itemView.startAnimation(animation);
                        notifyItemRemoved(position);
                        delete(id);
                        data.remove(position);
                    }

                }
            });
        }
    }

    public void update(String id,Product product){
        DocumentReference document = firestore.collection("Products").document(id);
        document.update("name",product.getName());
        document.update("stars",product.getStars());
        document.update("price",product.getPrice());
        document.update("amount",product.getAmount()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG,"Sikeres vásárlás");
                }else{
                    Log.i(LOG_TAG, "Sikertelen vásárlás");
                }
            }
        });
    }

    public void delete(String id){
        firestore.collection("Products").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG,"Sikeres törlés");
                }else{
                    Log.i(LOG_TAG,"Sikertelen törlés");
                }
            }
        });
    }

}


