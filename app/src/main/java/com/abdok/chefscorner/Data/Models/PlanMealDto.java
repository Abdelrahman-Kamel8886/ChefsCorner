package com.abdok.chefscorner.Data.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "plan_meal_table", primaryKeys = {"id", "mealId", "date"})
public class PlanMealDto implements Serializable {

    @NonNull
    private String id;
    @NonNull
    private String mealId;
    @NonNull
    private DateDTO date;
    private MealDTO meal;

    public PlanMealDto(String id, DateDTO date, MealDTO meal) {
        this.id = id;
        this.date = date;
        this.meal = meal;
    }

    public PlanMealDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateDTO getDate() {
        return date;
    }

    public void setDate(DateDTO date) {
        this.date = date;
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
