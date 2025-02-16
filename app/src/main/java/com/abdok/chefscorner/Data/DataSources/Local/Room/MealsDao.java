package com.abdok.chefscorner.Data.DataSources.Local.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.PlanMealDto;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealsDao {

    //Plan Meals

    @Query("SELECT * FROM plan_meal_table WHERE date = :dateDTO and id = :id")
    Single<List<PlanMealDto>> getAllMeals(DateDTO dateDTO , String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertToPlan(PlanMealDto planMeal);

    @Delete
    Completable deleteFromPlan(PlanMealDto planMeal);

    //Favourite Meals

    @Query("SELECT * FROM favourite_meals_table WHERE id = :id")
    Single<List<FavouriteMealDto>> getAllFavouriteMeals(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertToFavourite(FavouriteMealDto favouriteMealDto);

    @Delete
    Completable deleteFromFavourite(FavouriteMealDto favouriteMealDto);


    @Query("SELECT EXISTS(SELECT * FROM favourite_meals_table WHERE id = :id and mealId = :mealId)")
    Single<Boolean> isExistsInFavourite(String id , String mealId);

}
