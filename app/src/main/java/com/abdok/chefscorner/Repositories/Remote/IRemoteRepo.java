package com.abdok.chefscorner.Repositories.Remote;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public interface IRemoteRepo {

    Single<List<MealDTO>> getRandomMeals();
    Single<MealDTO> getMealDetails(int id);
    Single<CategoryMealsResponseDTO> getCategoriesMeals(String category);
    Single<CategoryMealsResponseDTO> getIngredientsMeals(String ingredient);
    Single<CategoryMealsResponseDTO> getAreaMeals(String area);
    Single<IngredientsNamesResponseDTO> getIngredientsNames();
    Single<AreasNamesResponseDTO> getAreasNames();
    Single<CategoriesNamesResponseDTO> getCategoriesNames();



}
