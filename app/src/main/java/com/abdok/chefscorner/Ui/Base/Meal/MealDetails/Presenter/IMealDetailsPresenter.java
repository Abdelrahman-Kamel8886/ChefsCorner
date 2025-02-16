package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter;

import com.abdok.chefscorner.Data.Models.MealDTO;

public interface IMealDetailsPresenter {

    void getMealDetails(int id);
    void clearDisposable();

    void addToFav(MealDTO mealDTO);

}
