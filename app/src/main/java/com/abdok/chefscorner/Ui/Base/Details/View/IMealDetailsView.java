package com.abdok.chefscorner.Ui.Base.Details.View;

import com.abdok.chefscorner.Models.MealDTO;

public interface IMealDetailsView {

    public void InitData(MealDTO mealDTO);
    public void showInformation(String message);

}
