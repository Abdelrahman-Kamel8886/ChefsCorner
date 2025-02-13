package com.abdok.chefscorner.Data.Repositories.Remote;

import com.abdok.chefscorner.Data.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Data.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;

import java.util.List;

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
