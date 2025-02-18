package com.abdok.chefscorner.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "history_meal_table")
public class HistoryDTO {

    @NonNull
    @PrimaryKey
    private String id;

    private MealDTO meal;


    public HistoryDTO(MealDTO meal) {
        this.meal = meal;
        this.id = meal.getIdMeal();
    }

    public HistoryDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MealDTO getMeal() {
        return meal;
    }

    public void setMeal(MealDTO meal) {
        this.meal = meal;
    }
}
