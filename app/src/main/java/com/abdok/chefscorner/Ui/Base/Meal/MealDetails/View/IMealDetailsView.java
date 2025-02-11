package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.View;

import com.abdok.chefscorner.Models.MealDTO;

public interface IMealDetailsView {

    public void InitData(MealDTO mealDTO);
    public void showInformation(String message);

}
