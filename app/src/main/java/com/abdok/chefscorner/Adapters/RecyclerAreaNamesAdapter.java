package com.abdok.chefscorner.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Consts;
import com.abdok.chefscorner.Utils.CountryFlagMapper;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerAreaNamesAdapter extends RecyclerView.Adapter<RecyclerAreaNamesAdapter.ViewHolder> {

    private List<AreasNamesResponseDTO.AreaNameDTO> list;

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerAreaNamesAdapter(List<AreasNamesResponseDTO.AreaNameDTO> list) {
        this.list = new ArrayList<>(list);
    }

    public void setAreas(List<AreasNamesResponseDTO.AreaNameDTO> areas){
        this.list = areas;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_name, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        Log.i("TAGlll", "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return list !=null? list.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title  , flag;
        MaterialCardView card;
        ConstraintLayout layout;

        int randomColor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.AreaTitle);
            flag = itemView.findViewById(R.id.flag);
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

        public void onBind(AreasNamesResponseDTO.AreaNameDTO areaName){
            String name = areaName.getStrArea();
            title.setText(name);
            card.setCardBackgroundColor(randomColor);
            flag.setText(CountryFlagMapper.getFlagEmoji(name));
            itemView.setOnClickListener(v -> {
                listener.onItemClick(name);
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String areaName);
    }
}
