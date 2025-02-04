package com.abdok.chefscorner.Ui.Home;

import com.abdok.chefscorner.Models.RandomMealsDTO;

import java.util.List;

public interface IHomeView {

    void showToast(String message);
    void showRandomMeals(List<RandomMealsDTO.MealsDTO> meals);
}
