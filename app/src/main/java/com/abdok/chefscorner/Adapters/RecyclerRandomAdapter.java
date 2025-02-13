package com.abdok.chefscorner.Adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RecyclerRandomAdapter extends RecyclerView.Adapter<RecyclerRandomAdapter.ViewHolder> {

    private List<MealDTO> meals;
    private onItemClickListener listener;

    public RecyclerRandomAdapter(List<MealDTO> meals) {
        this.meals = meals;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_random_meal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        
        Log.i("RecyclerAdapter", "onCreateViewHolder called");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals!=null?meals.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        MaterialCardView mealCard;
        MaterialButton addToPlanBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.meal_title);
            image = itemView.findViewById(R.id.meal_image);
            mealCard = itemView.findViewById(R.id.mealCard);
            addToPlanBtn = itemView.findViewById(R.id.addtoPlanBtn);

        }
        public void onBind(MealDTO mealDTO){
            title.setText(mealDTO.getStrMeal());

            mealCard.setOnClickListener(v -> listener.onItemClick(mealDTO));
            addToPlanBtn.setOnClickListener(v -> listener.onAddToPlanClick(meals.get(getAdapterPosition())));

            Glide.with(itemView.getContext())
                    .asBitmap()
                    .load(mealDTO.getStrMealThumb())
                    .placeholder(R.drawable.load)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            image.setImageBitmap(resource);
                            Bitmap retrievedBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                            mealDTO.setBitmap(retrievedBitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }
    public interface onItemClickListener{
        void onItemClick(MealDTO mealDTO);
        void onAddToPlanClick(MealDTO mealDTO);
    }
}
