package com.abdok.chefscorner.Ui.Base.Plan.View;

import com.abdok.chefscorner.Models.PlanMealDto;

import java.util.List;

public interface IPlanView {
    public void showMeals(List<PlanMealDto> meals);
}
