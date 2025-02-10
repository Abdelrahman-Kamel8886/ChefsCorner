package com.abdok.chefscorner.Models;

import java.util.List;

public class IngredientsNamesResponseDTO {


    private List<IngredientDTO> meals;

    public List<IngredientDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientDTO> meals) {
        this.meals = meals;
    }

    public static class IngredientDTO {
        private String idIngredient;
        private String strIngredient;
        private String strDescription;
        private Object strType;

        public String getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(String idIngredient) {
            this.idIngredient = idIngredient;
        }

        public String getStrIngredient() {
            return strIngredient;
        }

        public void setStrIngredient(String strIngredient) {
            this.strIngredient = strIngredient;
        }

        public String getStrDescription() {
            return strDescription;
        }

        public void setStrDescription(String strDescription) {
            this.strDescription = strDescription;
        }

        public Object getStrType() {
            return strType;
        }

        public void setStrType(Object strType) {
            this.strType = strType;
        }
    }
}
