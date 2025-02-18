package com.abdok.chefscorner.Data.DataSources.Local.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.abdok.chefscorner.Models.FavouriteMealDto;
import com.abdok.chefscorner.Models.HistoryDTO;
import com.abdok.chefscorner.Models.PlanMealDto;
import com.abdok.chefscorner.Utils.Helpers.Converter;


@Database(entities = {PlanMealDto.class , FavouriteMealDto.class , HistoryDTO.class}, version = 2)
@TypeConverters({Converter.class})
public abstract class LocalDataBase extends RoomDatabase {

    private static LocalDataBase instance;
    public abstract MealsDao mealsDao();


    public static synchronized void  initLocalDataBase(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                            LocalDataBase.class, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    private static LocalDataBase getInstance(){
        return instance;
    }

    public static MealsDao getDao(){
        return instance.mealsDao();
    }


}
