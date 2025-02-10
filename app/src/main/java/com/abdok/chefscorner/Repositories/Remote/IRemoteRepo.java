package com.abdok.chefscorner.Repositories.Remote;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IRemoteRepo {

    Flowable<RandomMealsResponseDTO> getRandomMeals();
    Single<CategoryMealsResponseDTO> getCategoriesMeals(String category);
    Single<IngredientsNamesResponseDTO> getIngredientsNames();
    Single<AreasNamesResponseDTO> getAreasNames();
    Single<CategoryMealsResponseDTO> getCategoriesNames();



}
