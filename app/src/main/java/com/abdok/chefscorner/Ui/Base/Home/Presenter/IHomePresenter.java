package com.abdok.chefscorner.Ui.Base.Home.Presenter;

import com.abdok.chefscorner.Models.DateDTO;
import com.abdok.chefscorner.Models.MealDTO;

public interface IHomePresenter {
    void start();
    void getRandomMeals();
    void getBreakFastMeals();
    void getDesertMeals();

    void addMealToPlan(MealDTO mealDTO , DateDTO date);
    void syncPlanMeals(String id);

    void syncFavouriteMeals(String id);

    void clearDisposable();
}
