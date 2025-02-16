package com.abdok.chefscorner.Data.Repositories.Backup;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.google.android.gms.tasks.Task;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IBackupRepo {


    Task<Void> savePlanMealToFirebase(MealDTO meal , DateDTO date);
    Task<Void> deletePlanMealFromFirebase(PlanMealDto dto);
    List<PlanMealDto> getPlanMeals(String id);

    Completable savePlanMealToLocal(PlanMealDto meal);
    Single<List<PlanMealDto> > getLocalPlanMeals(DateDTO dateDTO, String id);
    Completable deletePlanMealFromLocal(PlanMealDto meal);
}
