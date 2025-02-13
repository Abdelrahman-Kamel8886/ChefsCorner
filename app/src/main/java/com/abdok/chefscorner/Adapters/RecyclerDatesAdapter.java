package com.abdok.chefscorner.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.chefscorner.Models.DateDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.CountryFlagMapper;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDatesAdapter extends RecyclerView.Adapter<RecyclerDatesAdapter.ViewHolder> {

    private List<DateDTO> list;
    private int selectedPosition = -1;
    private onDateClickListener onDateClickListener;

    public RecyclerDatesAdapter(List<DateDTO> list) {
        this.list = new ArrayList<>(list);
    }


    public void setOnDateClickListener(RecyclerDatesAdapter.onDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(list.get(position));

        holder.card.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            onDateClickListener.onDateClick(list.get(position));
        });

        if(selectedPosition == position){
            holder.card.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.mainColor));
            holder.card.setStrokeColor(holder.itemView.getContext().getColor(R.color.mainColor));
            holder.backgroundDate.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.white));
            holder.date.setTextColor(holder.itemView.getContext().getColor(R.color.charcoal));
            holder.day.setVisibility(View.VISIBLE);
        }
        else{
            holder.card.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.DeepNavy));
            holder.card.setStrokeColor(holder.itemView.getContext().getColor(R.color.DeepNavy));
            holder.backgroundDate.setBackgroundColor(holder.itemView.getContext().getColor(R.color.DeepNavy));
            holder.date.setTextColor(holder.itemView.getContext().getColor(R.color.white));
            holder.day.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list !=null? list.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, day;
        MaterialCardView card;
        ConstraintLayout backgroundDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            card = itemView.findViewById(R.id.dayBtn);
            backgroundDate = itemView.findViewById(R.id.backgroundDate);
        }

        public void onBind(DateDTO dateDTO){
            date.setText(dateDTO.getSubDate());
            day.setText(dateDTO.getDay());


        }
    }

    public interface onDateClickListener{
        void onDateClick(DateDTO dateDTO);
    }

}
