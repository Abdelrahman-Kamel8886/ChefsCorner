package com.abdok.chefscorner.Utils;

import com.abdok.chefscorner.Models.CategoryResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsDTO;
import com.abdok.chefscorner.Models.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class SharedModel {
    public static UserDTO user ;
    private static ArrayList<RandomMealsDTO.MealsDTO> randomMeals ;
    private static List<CategoryResponseDTO.CategoryMealDTO> BreakfastMeals ;
    private static List<CategoryResponseDTO.CategoryMealDTO> DesertMeals ;

    public static ArrayList<RandomMealsDTO.MealsDTO> getRandomMeals() {
        return randomMeals;
    }

    public static void setRandomMeals(ArrayList<RandomMealsDTO.MealsDTO> randomMeals) {
        SharedModel.randomMeals = randomMeals;
    }

    public static List<CategoryResponseDTO.CategoryMealDTO> getBreakfastMeals() {
        return BreakfastMeals;
    }

    public static void setBreakfastMeals(List<CategoryResponseDTO.CategoryMealDTO> breakfastMeals) {
        BreakfastMeals = breakfastMeals;
    }

    public static List<CategoryResponseDTO.CategoryMealDTO> getDesertMeals() {
        return DesertMeals;
    }

    public static void setDesertMeals(List<CategoryResponseDTO.CategoryMealDTO> desertMeals) {
        DesertMeals = desertMeals;
    }

    public static UserDTO getUser() {
        return user;
    }

    public static void setUser(UserDTO user) {
        SharedModel.user = user;
    }
}
