package com.abdok.chefscorner.Utils;

import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Models.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class SharedModel {
    public static UserDTO user ;
    private static ArrayList<MealDTO> randomMeals ;
    private static List<CategoryMealsResponseDTO.CategoryMealDTO> BreakfastMeals ;
    private static List<CategoryMealsResponseDTO.CategoryMealDTO> DesertMeals ;

    public static ArrayList<MealDTO> getRandomMeals() {
        return randomMeals;
    }

    public static void setRandomMeals(ArrayList<MealDTO> randomMeals) {
        SharedModel.randomMeals = randomMeals;
    }

    public static List<CategoryMealsResponseDTO.CategoryMealDTO> getBreakfastMeals() {
        return BreakfastMeals;
    }

    public static void setBreakfastMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> breakfastMeals) {
        BreakfastMeals = breakfastMeals;
    }

    public static List<CategoryMealsResponseDTO.CategoryMealDTO> getDesertMeals() {
        return DesertMeals;
    }

    public static void setDesertMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> desertMeals) {
        DesertMeals = desertMeals;
    }

    public static UserDTO getUser() {
        return user;
    }

    public static void setUser(UserDTO user) {
        SharedModel.user = user;
    }
}
