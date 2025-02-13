package com.abdok.chefscorner.Ui.Base.Home;

import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Utils.SharedModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class HomePresenter implements IHomePresenter {

    private IHomeView view;
    private RemoteRepository remoteRepository;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(IHomeView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
    }

    @Override
    public void start() {
        getRandomMeals();
    }

    @Override
    public void getRandomMeals() {
        Disposable disposable = remoteRepository.getRandomMeals()
                .subscribe(meals -> {
                            SharedModel.setRandomMeals((ArrayList<MealDTO>) meals);
                            getBreakFastMeals();
                        }, throwable -> {
                            view.showMessage(throwable.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }


    @Override
    public void getBreakFastMeals() {

        Disposable breakfast = remoteRepository.getCategoriesMeals("Breakfast")
                .subscribe(
                        categoryMealsResponseDTO -> {
                            SharedModel.setBreakfastMeals(categoryMealsResponseDTO.getMeals());
                            getDesertMeals();
                        },
                        throwable -> {
                            view.showMessage(throwable.getMessage());
                        }
                );
        compositeDisposable.add(breakfast);
    }

    @Override
    public void getDesertMeals() {
        Disposable dessert = remoteRepository.getCategoriesMeals("Dessert")
                .subscribe(
                        categoryMealsResponseDTO -> {
                            SharedModel.setDesertMeals(categoryMealsResponseDTO.getMeals());
                            view.initView();
                        },
                        throwable -> {
                            view.showMessage(throwable.getMessage());
                        }

                );
        compositeDisposable.add(dessert);
    }
    @Override
    public void clearDisposable() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

}
