package com.abdok.chefscorner.Models;

import java.util.List;

public class AreasNamesResponseDTO {


    private List<AreaNameDTO> meals;

    public List<AreaNameDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<AreaNameDTO> meals) {
        this.meals = meals;
    }

    public static class AreaNameDTO {
        private String strArea;

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strArea) {
            this.strArea = strArea;
        }
    }
}
