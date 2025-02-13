package com.abdok.chefscorner.Data.DataSources.Local.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealsDao {

    @Query("SELECT * FROM plan_meal_table WHERE date = :dateDTO and id = :id")
    Single<List<PlanMealDto>> getAllMeals(DateDTO dateDTO , String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(PlanMealDto planMeal);
}
