package com.abdok.chefscorner.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.R;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RecyclerAllMealAdapter extends RecyclerView.Adapter<RecyclerAllMealAdapter.ViewHolder> {

    private List<CategoryMealsResponseDTO.CategoryMealDTO> meals;

    private onItemClickListener listener;

    public RecyclerAllMealAdapter(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        this.meals = meals;
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_big, parent, false);
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
        ConstraintLayout mealCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mealTitle);
            image = itemView.findViewById(R.id.mealImage);
            mealCard = itemView.findViewById(R.id.main);
        }
        public void onBind(CategoryMealsResponseDTO.CategoryMealDTO mealsDTO){
            title.setText(mealsDTO.getStrMeal());
            Glide.with(itemView.getContext())
                    .load(mealsDTO.getStrMealThumb())
                    .placeholder(R.drawable.load)
                    .into(image);
            mealCard.setOnClickListener(v -> {
                listener.onItemClick(Integer.parseInt(mealsDTO.getIdMeal()));
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(int id);
    }
}
