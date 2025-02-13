package com.abdok.chefscorner.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.R;
import com.bumptech.glide.Glide;
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
            Glide.with(itemView.getContext())
                    .load(mealDTO.getStrMealThumb())
                    .placeholder(R.drawable.load)
                    .into(image);
            mealCard.setOnClickListener(v -> listener.onItemClick(mealDTO));
            addToPlanBtn.setOnClickListener(v -> listener.onAddToPlanClick(meals.get(getAdapterPosition())));
        }
    }
    public interface onItemClickListener{
        void onItemClick(MealDTO mealDTO);
        void onAddToPlanClick(MealDTO mealDTO);
    }
}
