package com.abdok.chefscorner.Data.Repositories.Backup;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IBackupRepo {

    //plan meals

    Task<Void> savePlanMealToFirebase(MealDTO meal , DateDTO date);
    Task<Void> deletePlanMealFromFirebase(PlanMealDto dto);
    DatabaseReference getPlanMeals(String id);

    Completable savePlanMealToLocal(PlanMealDto meal);
    Single<List<PlanMealDto> > getLocalPlanMeals(DateDTO dateDTO, String id);
    Completable deletePlanMealFromLocal(PlanMealDto meal);
    Single<List<DateDTO>> getAllDaysMealAdded(String mealId, String id);
    Completable insertAllToPlan(List<PlanMealDto> planMeals);
    Completable clearPlanMealTable();

    //Favorite Meals

    Task<Void> saveFavoriteMealToFirebase(FavouriteMealDto meal);
    Task<Void> deleteFavoriteMealFromFirebase(FavouriteMealDto meal);
    DatabaseReference getFavoriteMeals(String id);
    Completable saveFavoriteMealToLocal(FavouriteMealDto meal);
    Single<List<FavouriteMealDto>> getLocalFavoriteMeals(String id);
    Completable deleteFavoriteMealFromLocal(FavouriteMealDto meal);
    Single<Boolean> isExistInFavorite(String id , String mealId);
    Completable clearFavoriteMealTable();
    Completable insertAllToFavorite(List<FavouriteMealDto> favoriteMeals);


}
