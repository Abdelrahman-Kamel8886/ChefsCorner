package com.abdok.chefscorner.Data.Models;

import java.util.List;

public class CategoryMealsResponseDTO {


    private List<CategoryMealDTO> meals;

    public List<CategoryMealDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<CategoryMealDTO> meals) {
        this.meals = meals;
    }

    public static class CategoryMealDTO {
        private String strMeal;
        private String strMealThumb;
        private String idMeal;

        public String getStrMeal() {
            return strMeal;
        }

        public void setStrMeal(String strMeal) {
            this.strMeal = strMeal;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }

        public void setStrMealThumb(String strMealThumb) {
            this.strMealThumb = strMealThumb;
        }

        public String getIdMeal() {
            return idMeal;
        }

        public void setIdMeal(String idMeal) {
            this.idMeal = idMeal;
        }
    }
}
