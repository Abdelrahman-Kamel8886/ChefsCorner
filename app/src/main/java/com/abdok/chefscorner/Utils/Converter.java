package com.abdok.chefscorner.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.UserDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;

public class Converter {

    private static Gson gson = new Gson();

    public static String userToJson(UserDTO user) {
        return gson.toJson(user);
    }

    public static UserDTO jsonToUser(String json) {
        return gson.fromJson(json, UserDTO.class);
    }

    @TypeConverter
    public static String fromDateDTO(DateDTO dateDTO) {
        return dateDTO == null ? null : gson.toJson(dateDTO);
    }

    @TypeConverter
    public static DateDTO toDateDTO(String dateString) {
        return dateString == null ? null : gson.fromJson(dateString, new TypeToken<DateDTO>() {}.getType());
    }

    @TypeConverter
    public static String fromMealDTO(MealDTO mealDTO) {
        return mealDTO == null ? null : gson.toJson(mealDTO);
    }

    @TypeConverter
    public static MealDTO toMealDTO(String mealString) {
        return mealString == null ? null : gson.fromJson(mealString, new TypeToken<MealDTO>() {}.getType());
    }
}
