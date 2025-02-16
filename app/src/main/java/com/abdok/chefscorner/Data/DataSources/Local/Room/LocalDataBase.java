package com.abdok.chefscorner.Data.DataSources.Local.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.Utils.Converter;


@Database(entities = {PlanMealDto.class , FavouriteMealDto.class}, version = 1)
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
