package com.abdok.chefscorner.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "favourite_meals_table", primaryKeys = {"id", "mealId"})
public class FavouriteMealDto implements Serializable {

    @NonNull
    private String id;
    @NonNull
    private String mealId;
    private MealDTO meal;

    public FavouriteMealDto(MealDTO meal, @NonNull String mealId, @NonNull String id) {
        this.meal = meal;
        this.mealId = mealId;
        this.id = id;
    }

    public FavouriteMealDto() {}

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

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
}
