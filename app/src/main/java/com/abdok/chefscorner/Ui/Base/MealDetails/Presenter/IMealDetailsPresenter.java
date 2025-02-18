package com.abdok.chefscorner.Ui.Base.MealDetails.Presenter;

import com.abdok.chefscorner.Models.DateDTO;
import com.abdok.chefscorner.Models.MealDTO;

public interface IMealDetailsPresenter {

    void getMealDetails(int id);
    void clearDisposable();
    void addToFav(MealDTO mealDTO);
    void removeFromFav(MealDTO mealDTO);
    void checkIfMealIsFav(MealDTO mealDTO);
    void addMealToPlan(MealDTO mealDTO , DateDTO date);

}
