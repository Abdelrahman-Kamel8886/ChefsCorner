package com.abdok.chefscorner.Ui.Base.Meal.AllMeals.View;

import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;

import java.util.List;

public interface IAllMealsView {

    void showMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
    void filterData(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
    void navigateUp();

}
