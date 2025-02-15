package com.abdok.chefscorner.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Data.Models.IngredientFormatDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Consts;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerIngredientsAdapter extends RecyclerView.Adapter<RecyclerIngredientsAdapter.ViewHolder> {

    private ArrayList<IngredientFormatDTO> ingredients;

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerIngredientsAdapter(ArrayList<IngredientFormatDTO> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients!=null?ingredients.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , measure;
        ImageView image;
        MaterialCardView card;

        int randomColor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ingredientTitle);
            measure = itemView.findViewById(R.id.ingredientMeasure);
            image = itemView.findViewById(R.id.ingredientImage);
            card = itemView.findViewById(R.id.imageCard);

            int[] colors = {
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_red),
                    ContextCompat.getColor(itemView.getContext(), R.color.warm_orange),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_yellow),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_green),
                    ContextCompat.getColor(itemView.getContext(), R.color.light_blue),
                    ContextCompat.getColor(itemView.getContext(), R.color.sky_blue),
                    ContextCompat.getColor(itemView.getContext(), R.color.lavender),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_pink)
            };

            randomColor = colors[new Random().nextInt(colors.length)];

        }

        public void onBind(IngredientFormatDTO ingredient){
            title.setText(ingredient.getTitle());
            measure.setText(ingredient.getMeasure());
            card.setCardBackgroundColor(randomColor);
            Glide.with(itemView.getContext())
                    .load(Consts.INGREDIENTS_IMAGES_URL+ingredient.getTitle()+".png")
                    .placeholder(R.drawable.no_photo)
                    .into(image);
            itemView.setOnClickListener(v -> listener.onItemClick(ingredient.getTitle()));
        }
    }
    public interface OnItemClickListener{
        void onItemClick(String name);
    }
}
