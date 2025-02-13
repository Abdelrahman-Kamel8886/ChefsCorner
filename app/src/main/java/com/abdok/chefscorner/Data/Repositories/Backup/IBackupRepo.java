package com.abdok.chefscorner.Data.Repositories.Backup;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IBackupRepo {
    void savePlanMeal(MealDTO meal , DateDTO date);
    void savePlanMealToFirebase(MealDTO meal , DateDTO date);
    void deletePlanMealFromFirebase(PlanMealDto dto);
    List<PlanMealDto> getPlanMeals(String id);

    void savePlanMealToLocal(PlanMealDto meal);
    Single<List<PlanMealDto> > getLocalPlanMeals(DateDTO dateDTO, String id);
}
