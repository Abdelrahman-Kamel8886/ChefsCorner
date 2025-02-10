package com.abdok.chefscorner.Network;

import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroServices {

    @GET("random.php")
    Single<RandomMealsResponseDTO> getRandomMeal();

    @GET("filter.php")
    Single<CategoryMealsResponseDTO> getMealsByCategory(@Query("c") String category);

    @GET("lookup.php")
    Single<RandomMealsResponseDTO> getMealDetails(@Query("i") int id);

}
