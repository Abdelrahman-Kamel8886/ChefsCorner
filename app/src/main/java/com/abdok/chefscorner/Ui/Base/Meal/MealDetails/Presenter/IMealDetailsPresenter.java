package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;

public interface IMealDetailsPresenter {

    void getMealDetails(int id);
    void clearDisposable();
    void addToFav(MealDTO mealDTO);
    void removeFromFav(MealDTO mealDTO);
    void checkIfMealIsFav(MealDTO mealDTO);
    void addMealToPlan(MealDTO mealDTO , DateDTO date);

}
