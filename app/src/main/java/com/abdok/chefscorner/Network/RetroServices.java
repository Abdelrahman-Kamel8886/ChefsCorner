package com.abdok.chefscorner.Network;

import com.abdok.chefscorner.Models.CategoryResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsDTO;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroServices {

    @GET("random.php")
    Single<RandomMealsDTO> getRandomMeal();

    @GET("filter.php")
    Single<CategoryResponseDTO> getMealsByCategory(@Query("c") String category);
}
