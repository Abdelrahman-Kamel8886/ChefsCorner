package com.abdok.chefscorner.Repositories.Remote;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.Network.RetroServices;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
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
    public Flowable<RandomMealsResponseDTO> getRandomMeals() {
        return retroServices.getRandomMeal()
                .repeat(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<CategoryMealsResponseDTO> getCategoriesMeals(String category) {
        return retroServices.getMealsByCategory(category)
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
