package com.abdok.chefscorner.Ui.Base.Meal.AllMeals.Presenter;

import com.abdok.chefscorner.Enums.SearchTypeEnum;

public interface IAllMealsPresenter {

    void getMeals(String title , SearchTypeEnum type);
    void getCategoriesMeals(String category);
    void getIngredientsMeals(String ingredient);
    void getAreaMeals(String area);
    void search(String query);
    void clearDisposable();
}
