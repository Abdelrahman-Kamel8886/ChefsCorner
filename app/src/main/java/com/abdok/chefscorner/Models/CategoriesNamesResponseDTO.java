package com.abdok.chefscorner.Models;

import java.util.List;

public class CategoriesNamesResponseDTO {


    private List<CategoryNameDTO> meals;

    public List<CategoryNameDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<CategoryNameDTO> meals) {
        this.meals = meals;
    }

    public static class CategoryNameDTO {
        private String strCategory;

        public String getStrCategory() {
            return strCategory;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }
    }
}
