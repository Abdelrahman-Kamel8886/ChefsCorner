package com.abdok.chefscorner.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Consts;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerCategoriesNamesAdapter extends RecyclerView.Adapter<RecyclerCategoriesNamesAdapter.ViewHolder> {

    private List<CategoriesNamesResponseDTO.CategoryNameDTO> list;

    private onItemClickListener onItemClickListener;

    public RecyclerCategoriesNamesAdapter(List<CategoriesNamesResponseDTO.CategoryNameDTO> list) {
        this.list = new ArrayList<>(list);
    }

    public void setOnItemClickListener(RecyclerCategoriesNamesAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_name, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, measure;
        ImageView image;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category_title);
            image = itemView.findViewById(R.id.categoryImage);
            card = itemView.findViewById(R.id.mainCard);


        }

        public void onBind(CategoriesNamesResponseDTO.CategoryNameDTO category) {

            title.setText(category.getStrCategory());
            String url = Consts.CATEGORIES_IMAGES_URL + category.getStrCategory() + ".png";
            if (category.getStrCategory().equals("Breakfast")) {
                url = "https://static.vecteezy.com/system/resources/previews/025/066/833/non_2x/breakfast-with-ai-generated-free-png.png";
            } else if (category.getStrCategory().equals("Goat")) {
                url = "https://png.pngtree.com/png-clipart/20231002/original/pngtree-goat-curry-halal-food-png-image_13067728.png";
            }

            Glide.with(itemView.getContext())
                    .load(url)
                    .into(image);

            card.setOnClickListener(v -> onItemClickListener.onItemClick(category));
        }
    }

    public interface onItemClickListener {
        void onItemClick(CategoriesNamesResponseDTO.CategoryNameDTO category);
    }

}
