package com.abdok.chefscorner.Network;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroServices {

    @GET("random.php")
    Single<RandomMealsResponseDTO> getRandomMeal();

    @GET("list.php?i=list")
    Single<IngredientsNamesResponseDTO> getIngredientsNames();

    @GET("list.php?a=list")
    Single<AreasNamesResponseDTO> getAreasNames();

    @GET("list.php?c=list")
    Single<CategoryMealsResponseDTO> getCategoriesNames();

    @GET("filter.php")
    Single<CategoryMealsResponseDTO> getMealsByCategory(@Query("c") String category);


    @GET("lookup.php")
    Single<RandomMealsResponseDTO> getMealDetails(@Query("i") int id);

}
