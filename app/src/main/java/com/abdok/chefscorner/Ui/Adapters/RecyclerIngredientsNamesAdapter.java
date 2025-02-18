package com.abdok.chefscorner.Ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Consts;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerIngredientsNamesAdapter extends RecyclerView.Adapter<RecyclerIngredientsNamesAdapter.ViewHolder> {

    private List<IngredientsNamesResponseDTO.IngredientDTO> ingredients;

    private RecyclerAreaNamesAdapter.OnItemClickListener listener;

    public void setListener(RecyclerAreaNamesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerIngredientsNamesAdapter(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

    public void setIngredients(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_name, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients!=null? ingredients.size() :0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , measure;
        ImageView image;
        MaterialCardView card;
        ConstraintLayout layout;

        int randomColor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ingredientTitle);
            image = itemView.findViewById(R.id.ingredientImage);
            card = itemView.findViewById(R.id.imageCard);
            layout = itemView.findViewById(R.id.main);

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

        public void onBind(IngredientsNamesResponseDTO.IngredientDTO ingredient){

            title.setText(ingredient.getStrIngredient());
            card.setCardBackgroundColor(randomColor);
            Glide.with(itemView.getContext())
                    .load(Consts.INGREDIENTS_IMAGES_URL+ingredient.getStrIngredient()+".png")
                    .placeholder(R.drawable.load)
                    .into(image);
            itemView.setOnClickListener(v -> listener.onItemClick(ingredient.getStrIngredient()));
        }
    }
    public interface OnItemClickListener{
        void onItemClick(String name);
    }
}
