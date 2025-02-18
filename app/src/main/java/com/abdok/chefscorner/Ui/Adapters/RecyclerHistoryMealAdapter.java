package com.abdok.chefscorner.Ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.FavouriteMealDto;
import com.abdok.chefscorner.Models.HistoryDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Helpers.CountryFlagMapper;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RecyclerHistoryMealAdapter extends RecyclerView.Adapter<RecyclerHistoryMealAdapter.ViewHolder> {

    private List<HistoryDTO> meals;

    private onItemClickListener listener;

    public void setMeals(List<HistoryDTO> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (meals.size() % 2 == 0 ){
            if (position == meals.size() - 1 || position == meals.size() - 2){
                holder.mainLayout.setVisibility(View.VISIBLE);
            }
        }
        else{
            if (position == meals.size() - 1){
                holder.mainLayout.setVisibility(View.VISIBLE);
            }
        }
        holder.onBind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals!=null?Integer.min(meals.size() , 6):0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , area , category;
        ImageView image;
        MaterialCardView mealCard;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            area = itemView.findViewById(R.id.area_title);
            category = itemView.findViewById(R.id.category_title);
            image = itemView.findViewById(R.id.local_img);
            mealCard = itemView.findViewById(R.id.card1);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
        public void onBind(HistoryDTO meal){
            title.setText(meal.getMeal().getStrMeal());
            area.setText(CountryFlagMapper.getFlagEmoji(meal.getMeal().getStrArea()) +" "+meal.getMeal().getStrArea());
            category.setText(meal.getMeal().getStrCategory());
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
        void onItemClick(HistoryDTO meal);
    }
}
