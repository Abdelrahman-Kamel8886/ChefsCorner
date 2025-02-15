package com.abdok.chefscorner.Adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Helpers.BitmapHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerPlanMealAdapter extends RecyclerView.Adapter<RecyclerPlanMealAdapter.ViewHolder> {

    private List<PlanMealDto> meals;

    private onItemClickListener listener;

    public RecyclerPlanMealAdapter() {
        this.meals = meals;
    }


    public void setMeals(List<PlanMealDto> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        ViewHolder holder = new ViewHolder(view);
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
        public void onBind(PlanMealDto meal){
            title.setText(meal.getMeal().getStrMeal());
            Glide.with(itemView.getContext())
                    .load(meal.getMeal().getStrMealThumb())
                    .placeholder(R.drawable.load)
                    .error(R.drawable.no_image)
                    .into(image);
//            Bitmap bitmap = BitmapHelper.getBitmapFromBase64(meal.getMeal().getBitmapBase64());
//            if (bitmap != null) {
//                image.setImageBitmap(bitmap);
//            }
//            else{
//                Glide.with(itemView.getContext())
//                        .load(meal.getMeal().getStrMealThumb())
//                        .placeholder(R.drawable.load)
//                        .into(image);
//            }
            mealCard.setOnClickListener(v -> {
                listener.onItemClick(meal);
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(PlanMealDto planMealDto);
    }
}
