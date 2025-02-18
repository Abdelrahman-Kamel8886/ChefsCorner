package com.abdok.chefscorner.Data.DataSources.Remote.Retrofit;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;

import io.reactivex.rxjava3.core.Single;
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
    Single<CategoriesNamesResponseDTO> getCategoriesNames();

    @GET("filter.php")
    Single<CategoryMealsResponseDTO> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<CategoryMealsResponseDTO> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<CategoryMealsResponseDTO> getMealsByArea(@Query("a") String area);


    @GET("lookup.php")
    Single<RandomMealsResponseDTO> getMealDetails(@Query("i") int id);

}
