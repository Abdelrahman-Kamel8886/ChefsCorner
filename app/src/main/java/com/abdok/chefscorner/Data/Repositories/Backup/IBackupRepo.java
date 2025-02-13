package com.abdok.chefscorner.Data.Repositories.Backup;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;

import java.util.List;

public interface IBackupRepo {
    void savePlanMeal(MealDTO meal , DateDTO date);
    void savePlanMealToFirebase(MealDTO meal , DateDTO date);
    void deletePlanMealFromFirebase(PlanMealDto dto);
    List<PlanMealDto> getPlanMeals(String id);

    void savePlanMealToLocal(PlanMealDto meal);
}
