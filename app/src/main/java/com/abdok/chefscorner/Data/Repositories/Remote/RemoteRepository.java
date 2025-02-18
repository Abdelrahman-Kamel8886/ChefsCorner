package com.abdok.chefscorner.Data.Repositories.Remote;

import com.abdok.chefscorner.Data.DataSources.Remote.Retrofit.RetroConnection;
import com.abdok.chefscorner.Data.DataSources.Remote.Retrofit.RetroServices;
import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RemoteRepository implements IRemoteRepo{

    RetroServices retroServices;
    private static RemoteRepository instance;

    private RemoteRepository(){
        this.retroServices = RetroConnection.getServices();
    }

    public static synchronized RemoteRepository getInstance(){
        if (instance == null) {
            instance = new RemoteRepository();

        }
        return instance;
    }

    @Override
    public Single<List<MealDTO>> getRandomMeals() {
        return retroServices.getRandomMeal()
                .repeat(10)
                .flatMapIterable(x->x.getMeals())
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<MealDTO> getMealDetails(int id) {
        return retroServices
                .getMealDetails(id)
                .subscribeOn(Schedulers.io())
                .map(item -> item.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<CategoryMealsResponseDTO> getCategoriesMeals(String category) {
        return retroServices.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<CategoryMealsResponseDTO> getIngredientsMeals(String ingredient) {
        return retroServices.getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<CategoryMealsResponseDTO> getAreaMeals(String area) {
        return retroServices.getMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Single<IngredientsNamesResponseDTO> getIngredientsNames() {
        return retroServices.getIngredientsNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<AreasNamesResponseDTO> getAreasNames() {
        return retroServices.getAreasNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<CategoriesNamesResponseDTO> getCategoriesNames() {
        return retroServices.getCategoriesNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
