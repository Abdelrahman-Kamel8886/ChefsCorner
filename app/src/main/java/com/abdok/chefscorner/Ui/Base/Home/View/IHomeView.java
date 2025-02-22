package com.abdok.chefscorner.Ui.Base.Home.View;

import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.HistoryDTO;
import com.abdok.chefscorner.Models.MealDTO;

import java.util.List;

public interface IHomeView {

    void initView();
    void showMessage(String message);
    void showError();
    void showRandomMeals(List<MealDTO> meals);
    void showBreakFastMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);
    void showDesertMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals);

    void showHistoryMeals(List<HistoryDTO> meals);
}
