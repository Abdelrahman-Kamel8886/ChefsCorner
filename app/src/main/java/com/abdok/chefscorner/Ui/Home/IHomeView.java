package com.abdok.chefscorner.Ui.Home;

import com.abdok.chefscorner.Models.CategoryResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsDTO;
import com.abdok.chefscorner.Models.UserDTO;

import java.util.List;

public interface IHomeView {

    void initView(UserDTO user);
    void showToast(String message);
    void showRandomMeals(List<RandomMealsDTO.MealsDTO> meals);
    void showBreakFastMeals(List<CategoryResponseDTO.CategoryMealDTO> meals);
    void showDesertMeals(List<CategoryResponseDTO.CategoryMealDTO> meals);
}
