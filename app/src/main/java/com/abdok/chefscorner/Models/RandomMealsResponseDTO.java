package com.abdok.chefscorner.Models;

import java.util.List;


public class RandomMealsResponseDTO {


    private List<MealDTO> meals;

    public List<MealDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDTO> meals) {
        this.meals = meals;
    }


}
