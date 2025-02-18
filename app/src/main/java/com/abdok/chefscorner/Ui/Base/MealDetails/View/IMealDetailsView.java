package com.abdok.chefscorner.Ui.Base.MealDetails.View;

import com.abdok.chefscorner.Models.MealDTO;

public interface IMealDetailsView {

    void InitData(MealDTO mealDTO);
    void showInformation(String message);
    void onAddedToFavSuccess(String message);
    void onRemovedFromFavSuccess(String message);
    void showError(String message);
    void toggleFavBtn(Boolean isExists);
    void onAddedToPlanSuccess(String message);

}
