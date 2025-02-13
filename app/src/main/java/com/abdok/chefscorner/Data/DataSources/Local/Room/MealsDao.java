package com.abdok.chefscorner.Data.DataSources.Local.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abdok.chefscorner.Data.Models.PlanMealDto;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MealsDao {

//    @Query("SELECT * FROM movie_table")
//    Single<List<MovieModel> > getAllMovies();
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PlanMealDto planMeal);

//    @Delete
//    Completable delete(MovieModel movie);

}
