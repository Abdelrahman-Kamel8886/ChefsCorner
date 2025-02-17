package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.View;

import com.abdok.chefscorner.Data.Models.MealDTO;

public interface IMealDetailsView {

    void InitData(MealDTO mealDTO);
    void showInformation(String message);
    void onAddedToFavSuccess(String message);
    void onRemovedFromFavSuccess(String message);
    void showError(String message);
    void toggleFavBtn(Boolean isExists);
    void onAddedToPlanSuccess(String message);

}
