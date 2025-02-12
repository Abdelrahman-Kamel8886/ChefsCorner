package com.abdok.chefscorner.Repositories.Remote;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public interface IRemoteRepo {

    Flowable<RandomMealsResponseDTO> getRandomMeals();
    Single<CategoryMealsResponseDTO> getCategoriesMeals(String category);
    Single<IngredientsNamesResponseDTO> getIngredientsNames();
    Single<AreasNamesResponseDTO> getAreasNames();
    Single<CategoriesNamesResponseDTO> getCategoriesNames();



}
