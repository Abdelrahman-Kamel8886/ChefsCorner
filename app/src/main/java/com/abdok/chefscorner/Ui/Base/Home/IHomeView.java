package com.abdok.chefscorner.Ui.Base.Home;

import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;

import java.util.List;

public interface IHomeView {

    void initView();
    void showToast(String message);
    void showRandomMeals(List<MealDTO> meals);
    void showBreakFastMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
    void showDesertMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
}
