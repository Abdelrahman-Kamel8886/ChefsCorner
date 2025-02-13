package com.abdok.chefscorner.Ui.Base.Home;

import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;

import java.util.List;

public interface IHomeView {

    void initView();
    void showMessage(String message);
    void showRandomMeals(List<MealDTO> meals);
    void showBreakFastMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
    void showDesertMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
}
